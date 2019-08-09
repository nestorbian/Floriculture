package com.nestor.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nestor.entity.Category;
import com.nestor.entity.ProductImage;
import org.springframework.util.StringUtils;

@Component
public class ViewConvertion {
	
	@Value("${image.product.base-url}")
	private String baseImageUrl;

	public String getFullImageUrl(String partImageUrl) {
		return StringUtils.isEmpty(partImageUrl) ? null : baseImageUrl.concat(partImageUrl);
	}
}
