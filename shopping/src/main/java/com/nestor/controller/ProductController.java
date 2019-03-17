package com.nestor.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.Constants;
import com.nestor.common.LogHttpInfo;
import com.nestor.common.ParameterException;
import com.nestor.entity.Product;
import com.nestor.entity.Result;
import com.nestor.service.ProductService;
import com.nestor.util.CheckUtil;
import com.nestor.vo.ExtProductView;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;
	
	/**
	 * <p>添加商品</p>
	 * @param product
	 * @return
	 */
	@PostMapping(path = "/admin/products")
	@LogHttpInfo
	public Result<Map<String, String>> add(@RequestBody Product product) {
		// base check
		commonCheck(product);
		if (product.getProductImages() == null || product.getProductImages().size() == 0) {
			throw new ParameterException("请上传至少一张商品图片");
		}
		
		if (product.getCategories() == null || product.getCategories().size() == 0) {
			throw new ParameterException("请选择一个所属分类");
		}
		
		Map<String, String> map = new HashMap<>();
		map.put(Constants.ID, service.add(product));
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
		// base check
		CheckUtil.notEmpty(product.getProductId(), "商品id不能为空");
		commonCheck(product);
		
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
	 * <p>design for admin-ui</p>
	 * <p>获取所有商品</p>
	 * @return
	 */
	@GetMapping(path = "/admin/products/{pageNumber}/{pageSize}")
	@LogHttpInfo
	public Result<Page<ExtProductView>> findAll(@PathVariable(name = "pageNumber") int pageNumber, 
			@PathVariable(name = "pageSize") int pageSize) {
		// base check
		CheckUtil.notLessThanEqualZero(pageNumber, "pageNumber不能小于等于0");
		CheckUtil.notLessThanEqualZero(pageSize, "pageSize不能小于等于0");
		
		return new Result<>(service.findAll(pageNumber, pageSize));
	}
	
	private void commonCheck(Product product) {
		CheckUtil.notEmpty(product.getProductName(), "商品名称不能为空");
		CheckUtil.notEmpty(product.getProductDescription(), "商品描述不能为空");
		CheckUtil.notNull(product.getProductOriginalPrice(), "商品原价不能为空");
		CheckUtil.notNull(product.getProductDiscountPrice(), "商品折扣价不能为空");
		CheckUtil.notNull(product.getProductStock(), "商品库存不能为空");
		CheckUtil.notEmpty(product.getFlowerMaterial(), "花材描述不能为空");
		CheckUtil.notEmpty(product.getProductPackage(), "包装描述不能为空");
		CheckUtil.notEmpty(product.getProductScene(), "场景描述不能为空");
		CheckUtil.notEmpty(product.getDistribution(), "配送描述不能为空");
		
		CheckUtil.notExceedMaxLength(product.getProductName(), 30, "商品名称最大长度不能超过30");
		CheckUtil.notExceedMaxLength(product.getProductDescription(), 200, "商品描述最大长度不能超过200");
		CheckUtil.notLessThanZero(product.getProductOriginalPrice(), "商品原价不能小于0");
		if (product.getProductDiscountPrice() != null) {
			CheckUtil.notLessThanZero(product.getProductDiscountPrice(), "商品折扣价不能小于0");
		}
		CheckUtil.notLessThanEqualZero(product.getProductStock(), "商品库存不能小于等于0");
		CheckUtil.notExceedMaxLength(product.getFlowerMaterial(), 100, "花材描述最大长度不能超过100");
		CheckUtil.notExceedMaxLength(product.getProductPackage(), 100, "包装描述最大长度不能超过100");
		CheckUtil.notExceedMaxLength(product.getProductScene(), 100, "场景描述最大长度不能超过100");
		CheckUtil.notExceedMaxLength(product.getDistribution(), 100, "配送描述最大长度不能超过100");
	}
	
	@GetMapping(path = "/test")
	public Result<?> testProduct() {
		return new Result<>(null);
	}
}
