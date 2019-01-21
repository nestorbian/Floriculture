package com.nestor.util;

import java.math.BigDecimal;

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
	
	public static void isExceedMaxLength(CharSequence string, long length, String msg) {
		if (string.length() > length) {
			throw new ParameterException(msg);
		}
	}
	
	public static void isUnderMinLength(CharSequence string, long length, String msg) {
		if (string.length() < length) {
			throw new ParameterException(msg);
		}
	}
	
	public static void isLessThanZero(BigDecimal value, String msg) {
		if (BigDecimal.ZERO.compareTo(value) > 0) {
			throw new ParameterException(msg);
		}
	}
	
	public static void isLessThanEqualZero(long value, String msg) {
		if (value <= 0) {
			throw new ParameterException(msg);
		}
	}
}
