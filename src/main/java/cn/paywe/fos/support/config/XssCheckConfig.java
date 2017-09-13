package cn.paywe.fos.support.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description xss防护攻击配合类
 * @author 李俊涛
 * @date 2017年6月21日
 */
//@DisconfFile(filename="xssCheck.properties")
public class XssCheckConfig {

	/**
	 * 版本变更标识
	 */
	private static String versionMark;

	//@DisconfFileItem(name="xssCheck.version.mark",associateField="versionMark")
	public static String getVersionMark() {
		return versionMark;
	}

	public static void setVersionMark(String versionMark) {
		XssCheckConfig.versionMark = versionMark;
	}
	/********************读取配置信息********************/
	private static ReentrantLock lock = new ReentrantLock();
	private static String versionLocal;
	private static Properties config;
	
	public static Properties getConfig() {
		if (config == null || getVersionMark() == null || !getVersionMark().equals(versionLocal)) {
			lock.lock();
			try {
				Properties properties = new Properties();//FileReaderUtils.getProperties("xssCheck.properties");
				config = properties;
				versionLocal = getVersionMark();
			} finally {
				lock.unlock();
			}
		}
		return config;
	}
	/********************读取配置信息********************/
	
	public static List<String> getAllNotContainValue(){
		Properties properties = getConfig();
		Set<Entry<Object,Object>> entrySet = properties.entrySet();
		List<String> list = new ArrayList<String>();
		if(entrySet!=null){
			for(Entry<Object,Object> entry : entrySet){
				if(!"xssCheck.version.mark".equals(entry.getKey())){
					Object value = entry.getValue();
					if(value != null && !"".equals(String.valueOf(value))){
						list.add(String.valueOf(value));
					}
				}
			}
		}
		return list;
	}
}
