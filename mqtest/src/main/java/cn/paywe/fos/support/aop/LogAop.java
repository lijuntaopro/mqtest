/**
 * 
 */
package cn.paywe.fos.support.aop;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import cn.paywe.fos.support.annotation.LogOperation;
import cn.paywe.fos.support.utils.date.DateUtils;
import cn.paywe.fos.support.utils.json.JsonUtils;




/**
 * @author 庄植雄
 * 2017年3月14日 下午3:05:57
 * copyright @frontpay 2017
 */

public class LogAop {
	protected final static Logger logger = LogManager.getLogger(LogAop.class);
		
	public void loggingPointcut() {
	}
	
	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		Date startTime = DateUtils.getCurrentDate();
		Object returnValue = proceedingJoinPoint.proceed();
		Date endTime = DateUtils.getCurrentDate();
		
		
		Annotation[] classAnnotations= proceedingJoinPoint.getTarget().getClass().getAnnotations();
		if(classAnnotations.length>0){
			for(Annotation a:classAnnotations){
				if(a instanceof LogOperation){					
					log(proceedingJoinPoint,returnValue,startTime,endTime);
					return returnValue;
				}
			}
		}
		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		Method method = signature.getMethod();
		Annotation[] methodAnnotations=method.getAnnotations();
		if(methodAnnotations.length>0){
			for(Annotation a:methodAnnotations){
				if(a instanceof LogOperation){				
					log(proceedingJoinPoint,returnValue,startTime,endTime);
					return returnValue;
				}
			}
		}
		
		return returnValue;
    }
	
	private void log(ProceedingJoinPoint proceedingJoinPoint,Object returnValue,Date startTime,Date endTime) throws Throwable{
		Map<String, Object> logBuilder = new LinkedHashMap<>();
		String className = proceedingJoinPoint.getTarget().getClass().getName();
		String methodName = proceedingJoinPoint.getSignature().getName();
		logBuilder.put("executedClass", String.format("%s.%s", className, methodName));
		
		Object[] args = proceedingJoinPoint.getArgs();
		List<Object> parameters = new ArrayList<>();
		for (Object arg : args) {
			if (arg instanceof Serializable) {
				parameters.add(arg);
			}
		}
		logBuilder.put("parameters", parameters);
		
		
		if (returnValue instanceof Serializable) {
			logBuilder.put("returnValue", returnValue);
		} else {
			logBuilder.put("returnValue", "returnValue is not Serializable");
		}
		
		logBuilder.put("startTime", DateUtils.formatDateTime(startTime));
		logBuilder.put("endTime", DateUtils.formatDateTime(endTime));
		logBuilder.put("consumedMillisecond", endTime.getTime() - startTime.getTime());
		
		logger.info(JsonUtils.toJson(logBuilder));
	}
}
