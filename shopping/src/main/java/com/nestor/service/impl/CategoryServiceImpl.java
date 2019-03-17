package com.nestor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nestor.common.BizException;
import com.nestor.common.DuplicateKeyException;
import com.nestor.entity.Category;
import com.nestor.repository.CategoryRepository;
import com.nestor.service.CategoryService;
import com.nestor.util.CheckUtil;
import com.nestor.util.IdUtil;
import com.nestor.util.JacksonUtil;
import com.nestor.vo.CategoryItemView;
import com.nestor.vo.CategoryView;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Value("${image.product.base-url}")
	private String baseImageUrl;

	@Override
	public String add(Category category) {
		String categoryId = IdUtil.generateId();
		category.setCategoryId(categoryId);
		// 测试级联插入
//		if (category.getProductList() != null && category.getProductList().size() != 0) {
//			category.getProductList().stream().forEach((item) -> {
//				item.setProductId(IdUtil.generateId());
//				item.setCategoryId(categoryId);
//			});
//		}
		category.setImageUrl(category.getImagePath());
		
		// 判断分类名称是否重复
		Category match = new Category();
		match.setCategoryName(category.getCategoryName());
		if (repository.exists(Example.of(match))) {
			throw new DuplicateKeyException("该分类名称已存在");
		}
		
		return repository.save(category).getCategoryId();
	}

	@Override
	public void update(Category category) {
		category.setImageUrl(category.getImagePath());
		
		if (repository.findById(category.getCategoryId()).get() == null) {
			throw new BizException("该商品分类已被删除");
		}
		
		repository.save(category);
	}

	@Override
	@Transactional
	public void delete(String id) {
		CheckUtil.notEmpty(id, "id不能为空");
		repository.deleteById(id);
		// 删除对应的商品 通过级联删除已完成
	}

	@Override
	public List<CategoryItemView> findCategories() {
		return repository.findCategories();
	}

	@Override
	public Page<CategoryView> findAll(int pageNumber, int pageSize) {
		Page<CategoryView> page = repository.findByOrderByCreateTimeDesc(PageRequest.of(pageNumber - 1, pageSize));
		
		return page;
	}

}
