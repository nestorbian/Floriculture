package com.nestor.service;


import org.springframework.data.domain.Page;

import com.nestor.entity.Product;
import com.nestor.vo.ExtProductView;

public interface ProductService {
	
	public String add(Product product);
	
	public void update(Product product);
	
	public void deleteById(String id);
	
	public Page<ExtProductView> findAll(int pageNumber, int pageSize);
	
	public ExtProductView findByProductId(String id);

}
