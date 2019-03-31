package com.nestor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nestor.entity.Product;
import com.nestor.vo.ExtProductView;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	public Page<ExtProductView> findByOrderByCreateTimeDesc(Pageable pageable);
	
	public ExtProductView findByProductId(String id);

}

