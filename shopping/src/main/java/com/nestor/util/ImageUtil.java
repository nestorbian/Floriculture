package com.nestor.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nestor.Constants;
import com.nestor.common.BizException;

/**
 * <p>图片工具类</p>
 * @author Lenovo
 *
 */
public class ImageUtil {

	/**
	 * <p>物理保存单张图片</p>
	 * @param baseImageDirectory
	 * @param imageFile
	 * @return
	 */
	public static String saveOne(String baseImageDirectory, String typeDirectory, MultipartFile imageFile) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String dateDirectory = LocalDateTime.now().format(dateTimeFormatter);
		
		StringBuilder imageDirectory = new StringBuilder();
		imageDirectory.append(baseImageDirectory);
		imageDirectory.append(Constants.SEPARATOR);
		imageDirectory.append(typeDirectory);
		imageDirectory.append(Constants.SEPARATOR);
		imageDirectory.append(dateDirectory);
		
		File directory = new File(imageDirectory.toString());
		if (!directory.exists()) {
			directory.mkdirs();
		}
		
		StringBuilder imageFileName = new StringBuilder(IdUtil.generateId());
		String originalFileName = imageFile.getOriginalFilename();
		imageFileName.append(originalFileName.substring(originalFileName.indexOf(".")));
		
		StringBuilder imageFullPath = new StringBuilder();
		imageFullPath.append(imageDirectory);
		imageFullPath.append(Constants.SEPARATOR);
		imageFullPath.append(imageFileName);
		
		try {
			imageFile.transferTo(new File(imageFullPath.toString()));
		} catch (IllegalStateException | IOException e) {
			throw new BizException("图片保存失败，请稍后重试");
		}
		
		StringBuilder result = new StringBuilder(Constants.SEPARATOR);
		result.append(typeDirectory);
		result.append(Constants.SEPARATOR);
		result.append(dateDirectory);
		result.append(Constants.SEPARATOR);
		result.append(imageFileName);
		
		return result.toString();
	}
	
	/**
	 * <p>物理保存多张图片</p>
	 * @param baseImageDirectory
	 * @param productImages
	 * @return
	 */
	public static List<String> saveMany(String baseImageDirectory, String typeDirectory, List<MultipartFile> productImages) {
		List<String> result = new ArrayList<>();
		
		productImages.stream().forEach((item) -> {
			result.add(saveOne(baseImageDirectory, typeDirectory, item));
		});
		
		return result;
	}
	
	/**
	 * <p>物理删除单张图片</p>
	 * @param imagePath
	 */
	public static void deleteOne(String imagePath) {
		File imageFile = new File(imagePath);
		if (imageFile.exists()) {
			imageFile.delete();
		}
	}
	
	/**
	 * <p>物理删除多张图片</p>
	 * @param imagePaths
	 */
	public static void deleteMany(List<String> imagePaths) {
		imagePaths.stream().forEach((item) -> {
			deleteOne(item);
		});
	}
}