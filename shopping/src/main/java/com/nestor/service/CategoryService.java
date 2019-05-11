package com.nestor.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nestor.entity.Category;
import com.nestor.vo.CategoryPageView;
import com.nestor.vo.CategoryItemView;
import com.nestor.vo.CategoryView;

public interface CategoryService {

    public String add(Category category);

    public void update(Category category);

    public void delete(String id);

    public List<CategoryItemView> findCategories();

    public Page<CategoryView> findAll(int pageNumber, int pageSize);

    public List<CategoryPageView> findCategoryInHome();

    public List<CategoryPageView> findAllCategory();
}
