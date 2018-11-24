package com.nestor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nestor.common.BizException;
import com.nestor.entity.Category;
import com.nestor.repository.CategoryRepository;
import com.nestor.service.CategoryService;
import com.nestor.util.CheckUtil;
import com.nestor.util.IdUtil;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Override
	public String add(Category category) {
		String categoryId = IdUtil.generateId();
		category.setCategoryId(categoryId);
		// 测试级联插入
		if (category.getProductList() != null && category.getProductList().size() != 0) {
			category.getProductList().stream().forEach((item) -> {
				item.setProductId(IdUtil.generateId());
				item.setCategoryId(categoryId);
			});
		}
		return repository.save(category).getCategoryId();
	}

	@Override
	public void update(Category category) {
		if (repository.findById(category.getCategoryId()).get() == null) {
			throw new BizException("该商品分类已被删除");
		}
		
		repository.save(category);
	}

	@Override
	@Transactional
	public void delete(String id) {
		CheckUtil.isEmpty(id, "id不能为空");
		repository.deleteById(id);
		// 删除对应的商品 通过级联删除已完成
	}

	@Override
	public List<Category> findAll() {
		return repository.findAll();
	}

}
