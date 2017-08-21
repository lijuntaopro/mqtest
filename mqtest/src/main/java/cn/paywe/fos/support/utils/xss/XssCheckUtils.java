package cn.paywe.fos.support.utils.xss;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.paywe.fos.support.annotation.XssCheckIgnore;
import cn.paywe.fos.support.utils.config.XssCheckConfig;
import cn.paywe.fos.support.utils.escape.EscapeUtils;

/**
 * @description 
 * @author 李俊涛
 * @date 2017年6月21日
 */
public class XssCheckUtils {
	
	private static Logger log = LogManager.getLogger(XssCheckUtils.class);
	
	/**
	 * @description 检查对象是否有非法字符
	 * @author 李俊涛
	 * @date 2017年6月21日
	 * @param obj
	 * @return
	 */
	public static boolean xssCheck(Object obj) {
		if(obj != null){
			//基本类型 和常见数字、日期类型不用检查
			if(notCheckClass(obj)){
				return true;
			}
			
			//字符串类型处理：真正要处理的类型
			if(obj instanceof String){
				return xssCheckString( (String)obj );
			}
			
			//集合类型处理
			if(obj instanceof Collection){
				return xssCheckCollection( (Collection)obj );
			}
			if(obj instanceof Map){
				return xssCheckMap( (Map)obj );
			}
			
			//数组处理
			if(isArray(obj)){
				List<Object> list = getListByNotBaseTypeArratObject(new ArrayList<Object>(), obj);
				if(list != null && list.size() != 0){
					return xssCheckCollection(list);
				}else{
					return true;
				}
			}
			
			//实体处理
			return xssCheckEntity(obj);
		}
		return true;
	}
	
	/**
	 * @description xss检查字符串
	 * @author 李俊涛
	 * @date 2017年6月21日
	 * @param str
	 * @return
	 */
	public static boolean xssCheckString(String str){
		List<String> list = XssCheckConfig.getAllNotContainValue();
		boolean checked = true;
		if(list != null && StringUtils.isNotBlank(str)){
			for(String string : list){
				if(StringUtils.isNotBlank(string)){
					checked = str.indexOf(string) == -1;
					if( !checked){
						log.error("字符串【{}】存在非法字符,不能存在【{}】", str, string);
						return false;
					}
				}
			}
		}
		return checked;
	}
	
	/**
	 * @description xss检查字符串数组
	 * @author 李俊涛
	 * @date 2017年6月26日
	 * @param string
	 * @return
	 */
	public static boolean xssCheckStrings(String[] string) {
		if(string != null){
			for(String s : string){
				boolean checkString = xssCheckString(s);
				if( !checkString){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @description xss检查集合
	 * @author 李俊涛 
	 * @date 2017年6月21日
	 * @param collection
	 * @return
	 */
	public static boolean xssCheckCollection(Collection<Object> collection){
		if(collection != null){
			Iterator<Object> iterator = collection.iterator();
			while(iterator.hasNext()){
				if( !xssCheck(iterator.next())){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @description xss检查map
	 * @author 李俊涛
	 * @date 2017年6月21日
	 * @param map
	 * @return
	 */
	public static boolean xssCheckMap(Map<Object,Object> map){
		if(map != null){
			Set<Entry<Object,Object>> entrySet = map.entrySet();
			for(Entry<Object,Object> entry : entrySet){
				if( !xssCheck(entry.getValue())){
					return false;
				}
			}
		
		}
		
		return true;
	}
	
	/**
	 * @description xss检查实体
	 * @author 李俊涛
	 * @date 2017年6月21日
	 * @param obj
	 * @return
	 */
	public static boolean xssCheckEntity(Object obj){
		if(obj != null){
			List<Field> flist = new ArrayList<Field>();
	
			EscapeUtils.getClassFieldAndMethod(obj.getClass(), flist);
	
			if (flist != null && !flist.isEmpty()) {
				for (Field field : flist) {
					if(field.isAnnotationPresent(XssCheckIgnore.class)){
						continue;
					}
					
					if (String.class == field.getType()) {
						String fieldValue = null;
						try {
							//强制获取字段值
							field.setAccessible(true);
							fieldValue = (String) field.get(obj);
						} catch (Exception e) {
							log.warn(e.getMessage());
						}
						//检查字符串
						if (StringUtils.isNotEmpty(fieldValue)){
							if( !xssCheckString(fieldValue)){
								return false;
							}
						}
					}
					//不是String对象，获取对象重新执行对象检查
					else{
						//获取属性值
						Object fieldValue = null;
						try {
							if(notCheckClass(obj)){
								return true;
							}
							field.setAccessible(true);
							fieldValue = field.get(obj);
						} catch (Exception e) {
							log.warn(e.getMessage());
						}
						
						//检查属性值，只有继承DtoBase才多重嵌套检查
						if (fieldValue != null && fieldValue instanceof Serializable){
							if( !xssCheck(fieldValue)){
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @description 基本类型 和常见数字、日期类型等不用检查
	 * @author 李俊涛
	 * @date 2017年6月21日
	 * @param obj
	 * @return
	 */
	public static boolean notCheckClass(Object obj){
		//基础类型不检查
		if(isBaseType(obj)){
			return true;
		}
		
		boolean isNotCheckClass = (obj instanceof Boolean) || (obj instanceof Number) || 
		(obj instanceof Character) ||  (obj instanceof Date)
		|| (obj instanceof java.sql.Date);
		
		return isNotCheckClass;
	}
	
	/**
	 * @description 获取一个xss过滤检查的Request
	 * @author 李俊涛
	 * @date 2017年6月26日
	 * @param obj
	 * @return
	 */
	public static HttpServletRequest getXssHttpServletRequest(HttpServletRequest obj){
		return null;
	}
	
	/**
	 * @description 把非基础类型的数组转为集合
	 * 如果为基础类型，放回空集合
	 * 如果类型不为数组，返回该类型集合
	 * obj如果为空，返回空聚合
	 * list可为空
	 * @author 李俊涛
	 * @date 2017年6月27日
	 * @param list 
	 * @param obj
	 * @return
	 */
	public static <T> List<Object> getListByNotBaseTypeArratObject(List<Object> list, Object obj){
		try{
			if(list == null){
				list = new ArrayList<Object>();
			}
			if(obj == null){
				return list;
			}
			
			if(isArray(obj)){
				if( !isBaseTypeArray(obj)){
					//基础类型不能强转
					Object[] objs = (Object[])obj;
					for(Object obj2 :objs){
						getListByNotBaseTypeArratObject(list, obj2);
					}
				}	
			}else{
				list.add(obj);
			}
		}catch(Exception e){
			
		}
		return list;
	}
	
	public static boolean isBaseType(Object obj){
		if(obj == null){
			return false;
		}
		return obj.getClass().isPrimitive();
	}
	
	public static boolean isBaseTypeArray(Object obj){
		if(isArray(obj)){
			Class<?> type = obj.getClass().getComponentType();
			if(type!=null && type.isPrimitive()){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isArray(Object obj){
		if(obj == null){
			return false;
		}
		return obj.getClass().isArray();
	}
	
}
