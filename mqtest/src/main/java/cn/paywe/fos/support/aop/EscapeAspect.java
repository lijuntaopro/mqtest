package cn.paywe.fos.support.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import cn.paywe.fos.support.annotation.Escape;
import cn.paywe.fos.support.utils.escape.EscapeUtils;

/**
 * aop 处理特殊字符转义
 * 
 * @author xsj
 *
 *
 * @date 2016年12月19日
 */
public class EscapeAspect {

	private static Logger log = LogManager.getLogger(EscapeUtils.class);

	// 只切Escape注解
	public void escapePointcut() {
	}

	public void ascapeAfterThrowing(JoinPoint joinPoint, Throwable e) {

		log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), e.getCause());

	}

	public Object escapeAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			Signature signature = joinPoint.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method targetMethod = methodSignature.getMethod();
			Annotation[][] ps = targetMethod.getParameterAnnotations();
			if (ps != null && ps.length > 0) {

				for (int i = 0; i < ps.length; i++) {

					Annotation[] annotations = ps[i];

					if (annotations != null && annotations.length > 0) {

						for (Annotation annotation : annotations) {

							if (annotation.annotationType() == Escape.class) {

								Escape e = (Escape) annotation;

								args[i] = EscapeUtils.escapeSpecialCharacter(args[i], e.value(), e.isAll());

								break;
							}
						}

					}
				}

			}
		}
		return joinPoint.proceed(args);
	}
}
