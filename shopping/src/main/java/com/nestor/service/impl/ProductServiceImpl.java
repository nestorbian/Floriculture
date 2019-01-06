package com.nestor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nestor.common.BizException;
import com.nestor.entity.Product;
import com.nestor.repository.ProductRepository;
import com.nestor.service.ProductService;
import com.nestor.util.CheckUtil;
import com.nestor.util.IdUtil;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository repository;

	@Override
	public String add(Product product) {
		product.setProductId(IdUtil.generateId());
		return repository.save(product).getProductId();
	}

	@Override
	public void update(Product product) {
		if (repository.findById(product.getProductId()).get() == null) {
			throw new BizException("该商品已被删除");
		}

		repository.save(product);
	}

	@Override
	public void deleteById(String id) {
		CheckUtil.isEmpty(id, "id不能为空");
		repository.deleteById(id);
	}

	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Product> findByCategoryId(String categoryId) {
		CheckUtil.isEmpty(categoryId, "categoryId不能为空");
		return repository.findByCategoryId(categoryId);
	}

}
