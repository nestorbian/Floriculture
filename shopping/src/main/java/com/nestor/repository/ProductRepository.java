package com.nestor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nestor.entity.Product;
import com.nestor.vo.ExtProductView;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	@Query(value = "SELECT p.product_id as product_id,pi.product_image_path as product_image_path,pi.product_image_url as product_image_url FROM product p INNER JOIN product_image pi ON p.product_id = pi.product_id", nativeQuery = true)
	public Object[] findProductView();
	
	public Page<ExtProductView> findByOrderByCreateTimeDesc(Pageable pageable);
}

