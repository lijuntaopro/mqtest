package cn.paywe.fos.support.utils.escape;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.paywe.fos.support.annotation.Escape;

/**
 * 特殊字符转移工具
 * 
 * @author xsj
 *
 *
 * @date 2016年12月17日
 */
public class EscapeUtils {

	private static Logger log = LogManager.getLogger(EscapeUtils.class);
	/**
	 * 转义对象包含特殊字符 使用注解 @Escape <br/>
	 * 只对String属性 有效
	 * 
	 * @param obj
	 *            被转义对象
	 * @param escapeChars
	 *            被转义字符
	 * @param isAll
	 *            是否全部转义String属性
	 * @return 转义后对象
	 */
	public static Object escapeSpecialCharacter(Object obj, String[] escapeChars, boolean isAll) {
		if (obj != null && escapeChars != null && escapeChars.length > 0) {

			try {
				if (obj.getClass() == String.class) {

					obj = toEscape(obj.toString(), escapeChars);

				} else {

					List<Field> flist = new ArrayList<Field>();

					getClassFieldAndMethod(obj.getClass(), flist);

					if (flist != null && !flist.isEmpty()) {
						for (Field field : flist) {

							if (String.class.getName().equals(field.getType().getName())) {
								if (isAll) {

									field.setAccessible(true);

									if (field.get(obj) != null && StringUtils.isNotEmpty(field.get(obj).toString()))
										field.set(obj, toEscape(field.get(obj).toString(), escapeChars));

								} else if (field.isAnnotationPresent(Escape.class)) {

									field.setAccessible(true);

									if (field.get(obj) != null && StringUtils.isNotEmpty(field.get(obj).toString()))
										field.set(obj, toEscape(field.get(obj).toString(), escapeChars));

								}
							}

						}

					}

				}
			} catch (SecurityException e) {
				log.error("Exception转义", e);
			} catch (IllegalArgumentException e) {
				log.error("Exception转义", e);
			} catch (IllegalAccessException e) {
				log.error("Exception转义", e);
			}
		}

		return obj;
	}

	/**
	 * 获取当前 以及父类 标记转义的字段
	 * lijuntao update 2017.06.23 修改为公共方法
	 * @param cur_class
	 * @param flist
	 */
	public static void getClassFieldAndMethod(Class<?> cur_class, List<Field> flist) {

		if (flist != null) {

			Field[] obj_fields = cur_class.getDeclaredFields();
			flist.addAll(Arrays.asList(obj_fields));
			if (cur_class.getSuperclass() != null) {
				getClassFieldAndMethod(cur_class.getSuperclass(), flist);
			}
		}

	}

	/**
	 * 转义字符
	 * 
	 * @param val
	 * @param params
	 * @return
	 */
	private static String toEscape(String val, String[] params) {

		if (StringUtils.isNotEmpty(val) && params != null && params.length > 0) {

			for (String string : params) {
				val = val.replace(string, "[" + string + "]");
			}
		}

		return val;
	}

}
