package com.nestor.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nestor.enums.ImageType;
import com.nestor.vo.Image;

public interface ImageService {
	
	public Image saveOneImage(MultipartFile image, ImageType type);
	public List<Image> saveManyImages(List<MultipartFile> images, ImageType type);
	
}
