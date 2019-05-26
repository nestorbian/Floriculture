package com.nestor.service;


import com.nestor.vo.ProductDetailView;
import org.springframework.data.domain.Page;

import com.nestor.entity.Product;
import com.nestor.vo.ExtProductView;
import com.nestor.vo.ProductView;
import com.nestor.vo.ProductWithSingleImage;

public interface ProductService {
	
	String add(Product product);
	
	void update(Product product);
	
	void deleteById(String id);
	
	Page<ExtProductView> findAll(int pageNumber, int pageSize);
	
	ExtProductView findByProductId(String id);

	ProductDetailView findProductById(String productId) throws Exception, IllegalAccessException;

	ProductWithSingleImage getProductWithSingleImage(String productId);
}
