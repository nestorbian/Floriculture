package com.nestor.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nestor.entity.Category;
import com.nestor.entity.ProductImage;

@Component
public class ViewConvertion {
	
	@Value("${image.product.base-url}")
	private String baseImageUrl;

	public String getFullImageUrl(String partImageUrl) {
		return baseImageUrl.concat(partImageUrl);
	}
}
