package com.nestor.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.SneakyThrows;

/**
 * JSON与Object互转的工具类
 * @author bianzeyang
 *
 */
public class JacksonUtil {
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	static {
	    // 转换为格式化的json
	    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

	    // 如果json中有新增的字段并且是实体类类中不存在的，不报错
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	@SneakyThrows
	public static String object2JsonStr(Object value) {
		return objectMapper.writeValueAsString(value);
	}
}
