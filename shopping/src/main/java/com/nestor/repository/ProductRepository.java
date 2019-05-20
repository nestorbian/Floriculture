package com.nestor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nestor.entity.Product;
import com.nestor.vo.ExtProductView;
import com.nestor.vo.ProductView;
import com.nestor.vo.ProductWithSingleImage;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	Page<ExtProductView> findByOrderByCreateTimeDesc(Pageable pageable);
	
	ExtProductView findByProductId(String id);

	@Query(value = "SELECT p FROM Product p WHERE p.productId = ?1")
	ProductView findDetailById(String id);
	
	@Query(value = "SELECT * FROM product_with_single_image WHERE productId = ?1", nativeQuery = true)
	ProductWithSingleImage getProductWithSingleImage(String productId);
}

