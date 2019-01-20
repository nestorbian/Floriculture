package com.nestor.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nestor.common.LogHttpInfo;
import com.nestor.entity.ProductImage;
import com.nestor.entity.Result;
import com.nestor.service.ProductImageService;

@RestController
public class ProductImageController {
	
	@Autowired
	private ProductImageService service;

	@PostMapping(path = "/product-images")
	@LogHttpInfo
	public Result<List<ProductImage>> physicalAdd(@RequestParam(name = "productImages") MultipartFile[] productImages) {
		return new Result<>(service.physicalAdd(Arrays.asList(productImages)));
	}
	
}
