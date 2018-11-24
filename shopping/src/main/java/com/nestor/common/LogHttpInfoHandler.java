package com.nestor.common;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nestor.util.JacksonUtil;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志打印切面
 * @author bianzeyang
 *
 */
@Aspect
@Component
@Slf4j
public class LogHttpInfoHandler {

    @Pointcut("@annotation(com.nestor.common.LogHttpInfo)")
	public void httpInfoPoint() {
		
	}
	
	@Before(value = "httpInfoPoint()")
	public void before(JoinPoint jp) {
		
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
		HttpServletRequest request = servletRequestAttributes.getRequest();
		Enumeration<String> enumeration = request.getHeaderNames();
		StringBuilder header = new StringBuilder();
		while(enumeration.hasMoreElements()) {
			header.append(enumeration.nextElement().concat(","));
		}
		log.info("前端请求url为：{}", request.getRequestURL());
		log.info("前端请求方法为：{}", request.getMethod());
		log.info("前端请求头为：{}", header.substring(0, header.length() - 1));
	    log.info("前端请求参数为：{}", JacksonUtil.object2JsonStr(jp.getArgs()));
	}
	
	@Around(value = "httpInfoPoint()")
	@SneakyThrows
	public Object around(ProceedingJoinPoint pjp) {
		String className = pjp.getSignature().getDeclaringTypeName();
	    String methodName = pjp.getSignature().getName();
	    Object result = null;
	    long start = System.currentTimeMillis();
	    result = pjp.proceed();
	    log.info(className + "." + methodName + "方法执行时长为:" + (System.currentTimeMillis() - start) + "ms");
	    return result;
	}
	
	@AfterReturning(value = "httpInfoPoint()", returning = "result")
	public void afterReturing(JoinPoint joinPoint, Object result) {
		log.info("返回结果：{}", JacksonUtil.object2JsonStr(result));
	}
}
