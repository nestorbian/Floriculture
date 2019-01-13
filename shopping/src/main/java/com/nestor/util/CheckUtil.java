package com.nestor.util;

import org.springframework.util.StringUtils;

import com.nestor.common.ParameterException;

public class CheckUtil {
	public static void isEmpty(CharSequence string, String msg) {
		if (StringUtils.isEmpty(string)) {
			throw new ParameterException(msg);
		}
	}
	
	public static void isNull(Object object, String msg) {
		if (object == null) {
			throw new ParameterException(msg);
		}
	}
}
