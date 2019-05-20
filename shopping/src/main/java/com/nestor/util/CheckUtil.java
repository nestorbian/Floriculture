package com.nestor.util;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nestor.common.ParameterException;
import com.nestor.enums.PictureFormat;

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

	/**
	 * value1不小于value2
	 * @param value1
	 * @param value2
	 * @param msg
	 */
	public static void notLessThan(Long value1, Long value2, String msg) {
		if (value1.compareTo(value2) < 0) {
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
	
	/**
	 * <p>检验是否为图片文件</p>
	 * @param file
	 * @param msg
	 */
	public static void imageFile(MultipartFile file, String msg) {
		String fileName = file.getOriginalFilename();
		String type = fileName.substring(fileName.lastIndexOf(".") + 1);
		boolean isImage = false;
		
		for (PictureFormat item : PictureFormat.values()) {
			if (type.toUpperCase().equals(item.toString())) {
				isImage = true;
			}
		}
		
		if (!isImage) {
			throw new ParameterException(msg);
		}
	}
	
	public static void imageFiles(MultipartFile[] files, String msg) {
		for (MultipartFile multipartFile : files) {
			imageFile(multipartFile, msg);
		}
	}
	
	public static void notEmptyList(List<?> list, String msg) {
		if (null == list || list.isEmpty()) {
			throw new ParameterException(msg);
		}
	}
}
