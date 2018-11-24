package com.nestor.service;

import java.util.List;

import com.nestor.entity.Category;

public interface CategoryService {
	
	public String add(Category category);
	
	public void update(Category category);
	
	public void delete(String id);
	
	public List<Category> findAll();
}
