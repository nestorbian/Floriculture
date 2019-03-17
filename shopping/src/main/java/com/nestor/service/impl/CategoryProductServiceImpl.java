package com.nestor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nestor.entity.CategoryProduct;
import com.nestor.repository.CategoryProductRepository;
import com.nestor.service.CategoryProductService;

@Service
public class CategoryProductServiceImpl implements CategoryProductService {
	
	@Autowired
	private CategoryProductRepository repository;

	@Override
	public void saveAll(List<CategoryProduct> categoryProducts) {
		repository.saveAll(categoryProducts);
	}

}
