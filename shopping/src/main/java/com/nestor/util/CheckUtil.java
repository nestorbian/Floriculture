package com.nestor.util;

import org.springframework.util.StringUtils;

import com.nestor.common.BizException;

public class CheckUtil {
	public static void isEmpty(CharSequence string, String msg) {
		if (StringUtils.isEmpty(string)) {
			throw new BizException(msg);
		}
	}
	
	public static void isNull(Object object, String msg) {
		if (object == null) {
			throw new BizException(msg);
		}
	}
}
