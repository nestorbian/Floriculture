package com.nestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nestor.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
