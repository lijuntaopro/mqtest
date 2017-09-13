/**
 * 
 */
package cn.paywe.fos.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 庄植雄
 * 2017年3月14日 下午5:20:45
 * copyright @frontpay 2017
 */
@Target({java.lang.annotation.ElementType.TYPE,java.lang.annotation.ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented 
public @interface LogOperation {

}