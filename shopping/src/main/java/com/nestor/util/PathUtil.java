package com.nestor.util;

import com.nestor.Constants;

/**
 * <p>path拼接</p>
 * @author Lenovo
 *
 */
public class PathUtil {

	public static String build(String... values) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < values.length; i ++) {
			if (i != 0) {
				stringBuilder.append(Constants.SEPARATOR);
			}
			stringBuilder.append(values[i]);
		}
		return stringBuilder.toString();
	}
	
}
