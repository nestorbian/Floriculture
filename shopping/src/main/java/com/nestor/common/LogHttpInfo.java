package com.nestor.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志打印注解
 * 用于方法上可以打印请求的参数，请求头，请求url，请求的方法
 * 以及注解所作用方法的执行所耗时间
 * @author bianzeyang
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogHttpInfo {
	String value() default "";
}
