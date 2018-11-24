package com.nestor.service;

import java.util.List;

import com.nestor.entity.Product;

public interface ProductService {
	
	public String add(Product product);
	
	public void update(Product product);
	
	public void deleteById(String id);
	
	public List<Product> findAll();
	
	public List<Product> findByCategoryId(String categoryId);
}
