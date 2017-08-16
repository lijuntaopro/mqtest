package cn.paywe.fos.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 转义注解 <br/>
 * 可继承
 * 
 * @author xsj
 *
 *
 * @date 2016年12月17日
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER ,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Escape {

	/**
	 * 默认转移的字符<br>
	 * "_" "%"
	 * 
	 * @return
	 */
	public String[] value() default { "_", "%" };

	/**
	 * 是否转义对象中String属性全部转义
	 * 
	 * @return true 全部转义 false 不全部转义(标记@Escape 注解属性还是会转义)
	 */
	public boolean isAll() default true;

}
