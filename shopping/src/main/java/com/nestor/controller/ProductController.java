package com.nestor.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nestor.Constants;
import com.nestor.common.LogHttpInfo;
import com.nestor.common.ParameterException;
import com.nestor.entity.Product;
import com.nestor.entity.Result;
import com.nestor.service.ProductService;
import com.nestor.util.CheckUtil;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;
	
	/**
	 * <p>添加商品</p>
	 * @param product
	 * @return
	 */
	@PostMapping(path = "/products")
	@LogHttpInfo
	public Result<Map<String, String>> add(@ModelAttribute Product product, MultipartFile[] images) {
		// 基础参数验证
		baseCheck(product);
		if (images == null || images.length == 0) {
			throw new ParameterException("请上传至少一张商品图片");
		}
		
		Map<String, String> map = new HashMap<>();
		map.put(Constants.ID, service.add(product, Arrays.asList(images)));
		return new Result<>(map);
	}
	
	/**
	 * <p>更新商品</p>
	 * @param product
	 * @return
	 */
	@PutMapping(path = "/products")
	@LogHttpInfo
	public Result<Boolean> update(@RequestBody Product product) {
		// 基础参数验证
		CheckUtil.notEmpty(product.getProductId(), "商品id不能为空");
		baseCheck(product);
		
		service.update(product);
		return new Result<>(true);
	}
	
	/**
	 * 删除商品
	 * @param id
	 * @return
	 */
	@DeleteMapping(path = "/products")
	@LogHttpInfo
	public Result<Boolean> delete(@RequestParam(value = "id") String id) {
		CheckUtil.notEmpty(id, "id不能为空");
		service.deleteById(id);
		return new Result<>(true);
	}
	
	/**
	 * 获取所有商品
	 * @return
	 */
	@GetMapping(path = "/products/{pageNumber}/{pageSize}")
	@LogHttpInfo
	public Result<Page<Product>> findAll(@PathVariable(name = "pageNumber") int pageNumber, 
			@PathVariable(name = "pageSize") int pageSize) {
		// 基础参数验证
		
		return new Result<>(service.findAll(pageNumber, pageSize));
	}
	
	private void baseCheck(Product product) {
		CheckUtil.notEmpty(product.getProductName(), "商品名称不能为空");
		CheckUtil.notEmpty(product.getProductDescription(), "商品描述不能为空");
		CheckUtil.notNull(product.getProductOriginalPrice(), "商品原价不能为空");
		CheckUtil.notNull(product.getProductDiscountPrice(), "商品折扣价不能为空");
		CheckUtil.notNull(product.getProductStock(), "商品库存不能为空");
		CheckUtil.notEmpty(product.getFlowerMaterial(), "花材描述不能为空");
		CheckUtil.notEmpty(product.getProductPackage(), "包装描述不能为空");
		CheckUtil.notEmpty(product.getProductScene(), "场景描述不能为空");
		
		CheckUtil.notExceedMaxLength(product.getProductName(), 50, "商品名称最大长度不能超过50");
		CheckUtil.notExceedMaxLength(product.getProductDescription(), 200, "商品描述最大长度不能超过200");
		CheckUtil.notLessThanZero(product.getProductOriginalPrice(), "商品原价不能小于0");
		CheckUtil.notLessThanZero(product.getProductDiscountPrice(), "商品折扣价不能小于0");
		CheckUtil.notLessThanEqualZero(product.getProductStock(), "商品库存不能小于等于0");
		CheckUtil.notExceedMaxLength(product.getFlowerMaterial(), 100, "花材描述最大长度不能超过100");
		CheckUtil.notExceedMaxLength(product.getProductPackage(), 100, "包装描述最大长度不能超过100");
	}
	
	@GetMapping(path = "/test")
	public Result<?> testProduct() {
		return new Result<>(service.findProductView());
	}
}
