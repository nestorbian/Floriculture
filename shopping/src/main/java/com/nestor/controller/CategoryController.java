package com.nestor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.Constants;
import com.nestor.entity.Category;
import com.nestor.entity.Result;
import com.nestor.service.CategoryService;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	/**
	 * 添加商品分类
	 * @param category
	 * @return
	 */
	@PostMapping(path = "/categories")
	public Result<Map<String, String>> add(@RequestBody Category category) {
		Map<String, String> map = new HashMap<>();
		map.put(Constants.ID, service.add(category));
		return new Result<>(map);
	}
	
	/**
	 * 更新商品分类
	 * @param category
	 * @return
	 */
	@PutMapping(path = "/categories")
	public Result<Boolean> update(@RequestBody Category category) {
		service.update(category);
		return new Result<>(true);
	}
	
	/**
	 * 删除商品分类
	 * @param id
	 * @return
	 */
	@DeleteMapping(path = "/categories")
	public Result<Boolean> delete(@RequestParam(value = "id") String id) {
		service.delete(id);
		return new Result<>(true);
	}
	
	/**
	 * 获取所有商品分类
	 * @return
	 */
	@GetMapping(path = "/categories")
	public Result<List<Category>> findAll() {
		return new Result<>(service.findAll());
	}
}
