package com.nestor.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nestor.entity.Category;

@Component
public class ViewConvertion {
	
	@Value("${image.product.base-url}")
	private String baseImageUrl;
	
	public String convertCategoryImageUrl(Category category) {
		return category.getImageUrl() == null ? null : baseImageUrl.concat(category.getImageUrl());
	}
}
