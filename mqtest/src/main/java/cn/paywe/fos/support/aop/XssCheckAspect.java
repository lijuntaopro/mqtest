package cn.paywe.fos.support.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import cn.paywe.fos.support.action.requestwrap.XssCheckHttpServletRequestWrapper;
import cn.paywe.fos.support.annotation.XssCheckIgnore;
import cn.paywe.fos.support.utils.xss.XssCheckUtils;

/**
 * @description 检查输入，对包含有<script等非法字符抛出异常
 * 注意：实体不可循环调用
 * @author 李俊涛
 * @date 2017年6月21日
 */
public class XssCheckAspect {

	private static Logger log = LogManager.getLogger(XssCheckAspect.class);

	public void escapePointcut() {
	}

	public void ascapeAfterThrowing(JoinPoint joinPoint, Throwable e) {

		log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), e.getCause());

	}

	public Object escapeAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object target = joinPoint.getTarget();
		Object[] args = joinPoint.getArgs();
		
		//类注解检查，有XssCheckIgnore注解，不检查xss
		Annotation[] classAnnotations = target.getClass().getAnnotations();
		if(classAnnotations != null){
			for(Annotation classAnnotation : classAnnotations){
				if(classAnnotation.annotationType() == XssCheckIgnore.class){
					return joinPoint.proceed(args);
				}
			}
		}
		
		if (args != null && args.length > 0) {
			Signature signature = joinPoint.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method targetMethod = methodSignature.getMethod();
			
			//方法注解检查，有XssCheckIgnore注解，不检查xss
			Annotation[] methodAnnotations = targetMethod.getAnnotations();
			if(methodAnnotations != null){
				for(Annotation methodAnnotation : methodAnnotations){
					if(methodAnnotation.annotationType() == XssCheckIgnore.class){
						return joinPoint.proceed(args);
					}
				}
			}
			
			Annotation[][] ps = targetMethod.getParameterAnnotations();
			
			//参数处理
			for(int n=0; n < args.length; n++){
				boolean isXssCheckIgnore = false;
				if( ps != null && ps.length > 0){
					
					//方法参数注解检查，有XssCheckIgnore注解，不检查xss
					Annotation[] annotations = ps[n];
					if(annotations != null){
						for (Annotation annotation : annotations) {
							if (annotation.annotationType() == XssCheckIgnore.class) {
								isXssCheckIgnore = true;
								break;
							}
						}
					}
					//如果方法参数不存在XssCheckIgnore注解，检查参数
					if( !isXssCheckIgnore){
						if(	args[n] instanceof HttpServletRequest ){
							//参数为HttpServletRequest，特殊处理
							args[n] = new XssCheckHttpServletRequestWrapper((HttpServletRequest)args[n]);
						}else if(!XssCheckUtils.xssCheck(args[n])){
							//其他实体，xss检查参数，不通过抛出异常
							throw new RuntimeException("0,存在非法字符");
						}
					}
					
				}
				
			}
		}
		return joinPoint.proceed(args);
	}
}
