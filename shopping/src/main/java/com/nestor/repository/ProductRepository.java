package com.nestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nestor.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}
