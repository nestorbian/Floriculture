package com.nestor.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nestor.Constants;
import com.nestor.enums.ImageType;
import com.nestor.service.ImageService;
import com.nestor.util.ImageUtil;
import com.nestor.vo.Image;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Value("${image.product.base-directory}")
	private String baseImageDirectory;
	
	@Value("${image.product.base-url}")
	private String baseImageUrl;

	@Override
	public Image saveOneImage(MultipartFile image, ImageType type) {
		String imagePath = ImageUtil.saveOne(baseImageDirectory, type.toString(), image);
		Image result = new Image();
		result.setImagePath(imagePath);
		StringBuilder stringBuilder = new StringBuilder(baseImageUrl);
		stringBuilder.append(imagePath);
		result.setImageUrl(stringBuilder.toString());
		return result;
	}

	@Override
	public List<Image> saveManyImages(List<MultipartFile> images, ImageType type) {
		List<String> imagePaths = ImageUtil.saveMany(baseImageDirectory, type.toString(), images);

		List<Image> ImageList = imagePaths.stream().map((item) -> {
			StringBuilder stringBuilder = new StringBuilder(baseImageUrl);
			stringBuilder.append(item);
			Image image = new Image();
			image.setImagePath(item);
			image.setImageUrl(stringBuilder.toString());
			return image;
		}).collect(Collectors.toList());
		
		return ImageList;
	}

}
