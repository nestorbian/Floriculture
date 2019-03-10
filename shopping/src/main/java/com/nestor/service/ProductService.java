package com.nestor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.nestor.entity.Product;
import com.nestor.entity.ProductView;

public interface ProductService {
	
	public String add(Product product, List<MultipartFile> productImages);
	
	public void update(Product product);
	
	public void deleteById(String id);
	
	public Page<Product> findAll(int pageNumber, int pageSize);
	
	public Object[] findProductView();
}
