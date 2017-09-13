package cn.paywe.fos.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 
 * 如果类有该属性，则类所有的方法都不检查
 * 如果方法有该属性，则该方法所有的参数不检查
 * 如果方法参数有该属性，则该参数不检查
 * 如果实体字段有注解，则该字段不检查
 * @author 李俊涛
 * @date 2017年6月21日
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER ,ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface XssCheckIgnore {

}
