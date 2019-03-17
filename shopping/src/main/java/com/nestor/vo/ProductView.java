package com.nestor.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.nestor.entity.ProductImage;

public interface ProductView {
	String getProductId();
	String getProductName();
	String getProductDescription();
	BigDecimal getProductOriginalPrice(); // 原价
	BigDecimal getProductDiscountPrice(); // 折扣价
	Long getProductStock(); // 库存
	String getFlowerMaterial(); // 花材
	String getProductPackage(); // 包装
	String getProductScene(); // 场景
	String getDistribution(); // 配送
	Long getSaleVolume(); // 销量
	LocalDateTime getCreateTime();
	LocalDateTime getUpdateTime();
	
	List<ProductImage> getProductImages();
}
