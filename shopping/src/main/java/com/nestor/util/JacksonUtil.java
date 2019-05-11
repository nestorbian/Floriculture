package com.nestor.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

/**
 * JSON与Object互转的工具类
 * @author bianzeyang
 *
 */
public class JacksonUtil {
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@SneakyThrows
	public static String object2JsonStr(Object value) {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
	}
}
