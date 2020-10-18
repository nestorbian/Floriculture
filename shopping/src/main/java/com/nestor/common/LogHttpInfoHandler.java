package com.nestor.common;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.nestor.util.IpUtil;
import com.nestor.util.JacksonUtil;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 日志打印切面
 * </p>
 * 
 * @author bianzeyang
 *
 */
@Aspect
@Component
@Slf4j
public class LogHttpInfoHandler {

	@Value("${aspectj.log.result.enable:false}")
	private boolean logResultEnable;

	/**
	 * 利用了@Inherited修饰的注解具有继承性，使得@LogHttpInfo修饰的接口或者类的子类可以继承该注解 从而使AOP的@within起作用 PS:@within针对目标对象的类具有指定注解修饰，对接口无用
	 *
	 * @param
	 * @return void
	 * @date : 2020/10/18 15:25
	 * @author : Nestor.Bian
	 * @since : 1.0
	 */
	@Pointcut("@within(com.nestor.common.LogHttpInfo)")
	public void httpInfoPoint() {

	}

	@Before(value = "httpInfoPoint()")
	public void before(JoinPoint jp) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
		HttpServletRequest request = servletRequestAttributes.getRequest();
		Enumeration<String> enumeration = request.getHeaderNames();
		StringBuilder header = new StringBuilder();
		while (enumeration.hasMoreElements()) {
			header.append(enumeration.nextElement().concat(","));
		}

		Object[] args = jp.getArgs();
		String[] parameterNames = ((MethodSignature) jp.getSignature()).getParameterNames();
		// 构建请求参数
		Map<String, Object> paramMap = buildRequestParam(parameterNames, args);

		HttpInfo httpInfo = HttpInfo.builder().remoteAddr(IpUtil.getRemoteAddr(request)).requestUrl(
				request.getRequestURL().toString()).httpMethod(request.getMethod()).requestHeader(
						header.substring(0, header.length() - 1)).requestParam(paramMap).build();

		log.info("请求信息：{}", JacksonUtil.object2JsonStr(httpInfo));
	}

	@Around(value = "httpInfoPoint()")
	@SneakyThrows
	public Object around(ProceedingJoinPoint pjp) {
		String className = pjp.getSignature().getDeclaringTypeName();
		String methodName = pjp.getSignature().getName();
		Object result;
		long start = System.currentTimeMillis();
		result = pjp.proceed();
		log.info(className + "." + methodName + "方法执行时长为:" + (System.currentTimeMillis() - start) + "ms");
		return result;
	}

	@AfterReturning(value = "httpInfoPoint()", returning = "result")
	public void afterReturing(JoinPoint joinPoint, Object result) {
		// 生产环境不允许打印返回结果，会导致日志过大，磁盘爆炸
		if (logResultEnable) {
			log.info("返回结果：\n{}", JacksonUtil.object2JsonStr(result));
		}
	}

	/**
	 * 构建请求参数
	 *
	 * @param parameterNames
	 * @param args
	 * @return void
	 * @date : 2020/10/18 17:29
	 * @author : Nestor.Bian
	 * @since : 1.0
	 */
	private Map<String, Object> buildRequestParam(String[] parameterNames, Object[] args) {
		Map<String, Object> paramMap = new HashMap<>(parameterNames.length);
		for (int i = 0; i < parameterNames.length; i++) {
			if (args[i] instanceof MultipartFile) {
				MultipartFile multipartFile = (MultipartFile) args[i];
				paramMap.put(parameterNames[i], multipartFile.getOriginalFilename());
			} else if (args[i] instanceof MultipartFile[]) {
				MultipartFile[] multipartFiles = (MultipartFile[]) args[i];
				Object[] fileNameArray = Arrays.stream(multipartFiles).map(
						MultipartFile::getOriginalFilename).toArray();
				paramMap.put(parameterNames[i], fileNameArray);
			} else {
				paramMap.put(parameterNames[i], args[i]);
			}
		}
		return paramMap;
	}

	@Data
	@Builder
	private static class HttpInfo {
		private String remoteAddr;
		private String requestUrl;
		private String httpMethod;
		private String requestHeader;
		private Map<String, Object> requestParam;
	}

}
