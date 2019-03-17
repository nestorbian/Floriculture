package com.nestor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nestor.entity.Category;
import com.nestor.vo.CategoryItemView;
import com.nestor.vo.CategoryView;

public interface CategoryRepository extends JpaRepository<Category, String> {
	
	public Page<CategoryView> findByOrderByCreateTimeDesc(Pageable pageable);
	
	@Query("SELECT c FROM Category c")
	public List<CategoryItemView> findCategories();
	
}
