package com.nestor.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nestor.entity.ProductImage;

public interface ProductImageService {

	public void add(ProductImage productImage);
	public List<ProductImage> physicalAdd(List<MultipartFile> productImages);
	public List<ProductImage> findByProductId(String productId);
	public void deleteByProductId(String productId);
	public void saveAll(List<ProductImage> productImages);
	public void saveOne(ProductImage productImage);
}
