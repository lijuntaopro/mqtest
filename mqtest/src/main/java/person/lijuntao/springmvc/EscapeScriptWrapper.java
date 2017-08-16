package person.lijuntao.springmvc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 过滤输入内容中的的特殊符号，防御跨站脚本攻击.<br/>
 * 对每个post请求的参数过滤一些关键字，替换成安全的，例如�?< > ' " \ / # &
 * 方法是实现一个自定义的HttpServletRequestWrapper，然后在Filter里面调用它，替换掉getParameter函数.<br/>
 * 参�?�：http://www.cnblogs.com/Mainz/archive/2012/11/01/2749874.html
 * 
 * @version 1.0, 2013-4-11,上午3:34:09.
 */
public class EscapeScriptWrapper extends HttpServletRequestWrapper {

	private static final Logger logger = LogManager.getLogger(EscapeScriptWrapper.class);

	public EscapeScriptWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		logger.debug("key={},value={}", parameter, values);
		if (values == null) {
			return null;
		}
		cleanXSS(values); // 传引�?
		return values;
	}

	/**
	 * 对单�?参数值进行过�?.
	 */
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		logger.debug("key={},value={}", parameter, value);
		return cleanXSS(value);
	}

	public String getHeader(String name) {
		// log.info("=== getHeader");
		String value = super.getHeader(name);
		logger.debug("name={},value={}", name, value);
		return cleanXSS(value);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> parameterMap = super.getParameterMap();
		Map<String, String[]> map = new HashMap<String, String[]>();
		Iterator itr = parameterMap.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			String[] str = parameterMap.get(key);
			if (str != null) {
				for (int i = 0; i < str.length; i++) {
					str[i] = cleanXSS(str[i]);
				}
			}
			map.put(key, str);
		}
		return map;

	}

	/**
	 * 对字符串数组进行过滤
	 * 
	 * @param values
	 */
	private void cleanXSS(String[] values) {
		if (values != null) {
			for (int num = 0; num < values.length; num++) {
				values[num] = this.cleanXSS(values[num]);
			}
		}
	}

	/**
	 * HTML过滤危险内容.
	 * 
	 * @param value
	 * @return
	 */
	private String cleanXSS(String value) {
		System.out.println("value=" + value);
		return value;
	}

}