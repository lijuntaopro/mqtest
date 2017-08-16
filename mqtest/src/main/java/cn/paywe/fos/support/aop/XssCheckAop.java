package cn.paywe.fos.support.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import cn.paywe.fos.support.aop.XssCheckAspect;

/**
 * @description 
 * 将所有的项目的controller层都拦截，进行xss检查
 * 如果有哪些controller，或者controller方法，或者接受的实体DTO等不想检查，请使用XssCheckIgnore注解
 * 
 * @author 李俊涛
 * @date 2017年6月21日
 */

@Aspect
@Component
public class XssCheckAop extends XssCheckAspect {

	@Pointcut("execution(* cn.paywe.fos.*.controller..*.*(..)))")
	public void myPointcut() {
	}

	@AfterThrowing(pointcut = "myPointcut()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Throwable e) {
		super.ascapeAfterThrowing(joinPoint, e);
	}

	@Around("myPointcut()")
	public Object escape(ProceedingJoinPoint joinPoint) throws Throwable {
		return super.escapeAround(joinPoint);
	}
}
