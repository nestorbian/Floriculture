package com.nestor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nestor.Constants;
import com.nestor.common.BizException;
import com.nestor.common.DuplicateKeyException;
import com.nestor.entity.Product;
import com.nestor.entity.ProductImage;
import com.nestor.repository.ProductRepository;
import com.nestor.service.ProductImageService;
import com.nestor.service.ProductService;
import com.nestor.util.IdUtil;
import com.nestor.util.ImageUtil;
import com.nestor.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Value("${image.product.base-directory}")
	private String baseProductDirectory;
	
	@Autowired
	private ProductImageService productImageService;

	@Override
	@Transactional
	public String add(Product product, List<MultipartFile> productImages) {
		String productId = IdUtil.generateId();
		product.setProductId(productId);
		
		// 判断商品名称是否重复
		Product match = new Product();
		match.setProductName(product.getProductName());
		if (repository.exists(Example.of(match))) {
			throw new DuplicateKeyException("该商品名称已存在");
		}
		
		String result = repository.save(product).getProductId();
		
		List<String> imagePaths = ImageUtil.saveMany(baseProductDirectory, Constants.PRODUCT, productImages);
		imagePaths.stream().forEach((item) -> {
			ProductImage productImage = new ProductImage();
			productImage.setProductImageId(IdUtil.generateId());
			productImage.setProductId(productId);
			productImage.setProductImagePath(item);
			productImage.setProductImageUrl(item);
			productImageService.add(productImage);
		});
		
		return result;
	}

	@Override
	@Transactional
	public void update(Product product) {
		if (repository.findById(product.getProductId()).get() == null) {
			throw new BizException("该商品已被删除");
		}
		
		List<ProductImage> productImages = product.getProductImages();
		repository.save(product);
		
		productImageService.deleteByProductId(product.getProductId());
		productImages.stream().forEach((item) -> {
			item.setProductId(product.getProductId());
			item.setProductImageId(IdUtil.generateId());
			String productImageUrl = item.getProductImageUrl().substring(PathUtil.build(baseProductDirectory, Constants.PRODUCT).length());
			item.setProductImageUrl(productImageUrl);
		});
		productImageService.saveAll(productImages);
	}

	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}

	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}

}
