package com.nestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nestor.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, String> {
	
	public List<ProductImage> findByProductId(String productId);
	public void deleteByProductId(String productId);
	public void deleteByProductIdIsAndProductImageIdNotIn(String productId, List<String> productImageIds);
	
}
