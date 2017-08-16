package person.lijuntao.springmvc;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @description 这个过滤器一定在编码过滤器之后，防止对没解码的参数�?�进行过滤（我们要过滤的是解码后要放入数据库的数据）
 * @author 李俊�?
 * @date 2017�?6�?21�?
 */
public class XssFilter extends  OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		EscapeScriptWrapper scriptWrapper = new EscapeScriptWrapper(request);
		filterChain.doFilter(scriptWrapper, response);
	}

}
