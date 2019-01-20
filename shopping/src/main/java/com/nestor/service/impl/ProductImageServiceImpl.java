package com.nestor.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nestor.Constants;
import com.nestor.entity.ProductImage;
import com.nestor.repository.ProductImageRepository;
import com.nestor.service.ProductImageService;
import com.nestor.util.ImageUtil;

@Service
public class ProductImageServiceImpl implements ProductImageService {
	
	@Autowired
	private ProductImageRepository repository;
	
	@Value("${image.product.base-directory}")
	private String baseProductDirectory;
	
	@Value("${image.product.base-url}")
	private String baseProductUrl;

	@Override
	public void add(ProductImage productImage) {
		repository.save(productImage);
	}

	@Override
	public List<ProductImage> physicalAdd(List<MultipartFile> productImages) {
		List<String> productPaths = ImageUtil.saveMany(baseProductDirectory, Constants.PRODUCT, productImages);
		List<ProductImage> productImageList = productPaths.stream().map((item) -> {
			StringBuilder stringBuilder = new StringBuilder(baseProductUrl);
			stringBuilder.append(item);
			ProductImage productImage = new ProductImage();
			productImage.setProductImagePath(item);
			productImage.setProductImageUrl(stringBuilder.toString());
			return productImage;
		}).collect(Collectors.toList());
		
		return productImageList;
	}

	@Override
	public List<ProductImage> findByProductId(String productId) {
		return repository.findByProductId(productId);
	}

	@Override
	public void deleteByProductId(String productId) {
		repository.deleteByProductId(productId);
	}

	@Override
	public void saveAll(List<ProductImage> productImages) {
		repository.saveAll(productImages);
	}

}
