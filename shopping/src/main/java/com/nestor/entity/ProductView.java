package com.nestor.entity;

import java.math.BigDecimal;
import java.util.List;

public interface ProductView {
	String getProductId();
	String getProductName();
	BigDecimal getProductOriginalPrice(); // 原价
	BigDecimal getProductDiscountPrice(); // 折扣价
//	List<Image> getProductImages();
//	
//	interface Image {
//		String getProductImageUrl();
//	}
}
