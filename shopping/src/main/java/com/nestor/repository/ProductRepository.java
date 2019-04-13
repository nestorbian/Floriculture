package com.nestor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nestor.entity.Product;
import com.nestor.vo.ExtProductView;
import com.nestor.vo.ProductView;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	public Page<ExtProductView> findByOrderByCreateTimeDesc(Pageable pageable);
	
	public ExtProductView findByProductId(String id);

	@Query(value = "SELECT p FROM Product p WHERE p.productId = ?1")
	public ProductView findDetailById(String id);
}

