package com.nestor.util;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import com.nestor.common.ParameterException;

public class CheckUtil {
	public static void notEmpty(CharSequence string, String msg) {
		if (StringUtils.isEmpty(string)) {
			throw new ParameterException(msg);
		}
	}
	
	public static void notNull(Object object, String msg) {
		if (object == null) {
			throw new ParameterException(msg);
		}
	}
	
	public static void notExceedMaxLength(CharSequence string, long length, String msg) {
		if (string.length() > length) {
			throw new ParameterException(msg);
		}
	}
	
	public static void notUnderMinLength(CharSequence string, long length, String msg) {
		if (string.length() < length) {
			throw new ParameterException(msg);
		}
	}
	
	public static void notLessThanZero(BigDecimal value, String msg) {
		if (BigDecimal.ZERO.compareTo(value) > 0) {
			throw new ParameterException(msg);
		}
	}
	
	public static void notLessThanEqualZero(long value, String msg) {
		if (value <= 0) {
			throw new ParameterException(msg);
		}
	}
}
