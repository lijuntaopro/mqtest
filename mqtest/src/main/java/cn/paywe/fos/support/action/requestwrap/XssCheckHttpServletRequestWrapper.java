package cn.paywe.fos.support.action.requestwrap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.paywe.fos.support.exception.BizException;
import cn.paywe.fos.support.utils.xss.XssCheckUtils;


public class XssCheckHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private static Logger log = LogManager.getLogger(XssCheckHttpServletRequestWrapper.class);
	
	public XssCheckHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}
	
	
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		cleanXSS(values); // 传引用
		return values;
	}

	/**
	 * 对单一参数值进行过滤.
	 */
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		return cleanXSS(value);
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		return cleanXSS(value);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map getParameterMap() {
		Map parameterMap = super.getParameterMap();
		Map map = new HashMap();
		Iterator itr = parameterMap.keySet().iterator();
		while(itr.hasNext()){
			String key = (String) itr.next();
			String[] str = (String[]) parameterMap.get(key);
			if(str != null){
				for(int i = 0; i < str.length; i++){
					str[i] = cleanXSS(str[i]);
				}
			}
			map.put(key, str);
		}
		return map;
		   
	 }


	private String cleanXSS(String string) {
		boolean CheckString = XssCheckUtils.xssCheck(string);
		if( !CheckString){
			throw new BizException( 0 , "存在非法字符");
		}
		return string;
	}
	
	private String[] cleanXSS(String[] strings) {
		boolean CheckString = XssCheckUtils.xssCheckStrings(strings);
		if( !CheckString){
			throw new BizException( 0 , "存在非法字符");
		}
		return strings;
	}
}