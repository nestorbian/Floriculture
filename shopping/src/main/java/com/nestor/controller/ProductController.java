package com.nestor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.Constants;
import com.nestor.entity.Product;
import com.nestor.entity.Result;
import com.nestor.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;
	
	/**
	 * 添加商品
	 * @param product
	 * @return
	 */
	@PostMapping(path = "/products")
	public Result<Map<String, String>> add(@RequestBody Product product) {
		Map<String, String> map = new HashMap<>();
		map.put(Constants.ID, service.add(product));
		return new Result<>(map);
	}
	
	/**
	 * 更新商品
	 * @param product
	 * @return
	 */
	@PutMapping(path = "/products")
	public Result<Boolean> update(@RequestBody Product product) {
		service.update(product);
		return new Result<>(true);
	}
	
	/**
	 * 删除商品
	 * @param id
	 * @return
	 */
	@DeleteMapping(path = "/products")
	public Result<Boolean> delete(@RequestParam(value = "id") String id) {
		service.deleteById(id);
		return new Result<>(true);
	}
	
	/**
	 * 获取所有商品
	 * @return
	 */
	@GetMapping(path = "/products")
	public Result<List<Product>> findAll() {
		return new Result<>(service.findAll());
	}
	
	/**
	 * 根据categoryId获取商品列表
	 * @param categoryId
	 * @return
	 */
	@GetMapping(path = "/products/{categoryId}")
	public Result<List<Product>> findByCategoryId(@PathVariable String categoryId) {
		return new Result<>(service.findByCategoryId(categoryId));
	}
}
