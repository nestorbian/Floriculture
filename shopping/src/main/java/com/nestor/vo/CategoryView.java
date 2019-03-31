package com.nestor.vo;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

public interface CategoryView {

	String getCategoryId();
	String getCategoryName();
	String getCategoryDescription();
	String getImagePath();
	@Value("#{@viewConvertion.getCategoryImageFullUrl(target)}")
	String getImageUrl();
	Boolean getNeedShowInHome();
	LocalDateTime getCreateTime();
	LocalDateTime getUpdateTime();
	// List<ProductView> getProductList();
	
}
