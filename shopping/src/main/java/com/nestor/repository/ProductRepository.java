package com.nestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nestor.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	public void deleteByCategoryId(String categoryId);
	
	public List<Product> findByCategoryId(String categoryId);
}
