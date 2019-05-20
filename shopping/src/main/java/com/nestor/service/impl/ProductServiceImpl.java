package com.nestor.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nestor.Constants;
import com.nestor.common.BizException;
import com.nestor.common.DuplicateKeyException;
import com.nestor.entity.Category;
import com.nestor.entity.CategoryProduct;
import com.nestor.entity.Product;
import com.nestor.entity.ProductImage;
import com.nestor.repository.ProductRepository;
import com.nestor.service.CategoryProductService;
import com.nestor.service.ProductImageService;
import com.nestor.service.ProductService;
import com.nestor.util.IdUtil;
import com.nestor.vo.ExtProductView;
import com.nestor.vo.ProductView;
import com.nestor.vo.ProductWithSingleImage;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Value("${image.product.base-directory}")
	private String baseProductDirectory;
	
	@Autowired
	private ProductImageService productImageService;
	
	@Autowired
	private CategoryProductService categoryProductService;

	@Override
	@Transactional
	public String add(Product product) {
		String productId = IdUtil.generateId();
		product.setProductId(productId);
		
		// 判断商品名称是否重复
		Product match = new Product();
		match.setProductName(product.getProductName());
		if (repository.exists(Example.of(match))) {
			throw new DuplicateKeyException("该商品名称已存在");
		}
		
		product.setSaleVolume(Long.valueOf(Constants.ZERO));
		String result = repository.save(product).getProductId();
		
		// 添加商品图片
		product.getProductImages().forEach((item) -> {
			item.setProductImageId(IdUtil.generateId());
			item.setProductId(result);
			item.setImageUrl(item.getImagePath());
			productImageService.saveOne(item);
		});
		
		// 添加商品与分类的关系
		List<CategoryProduct> categoryProducts = product.getCategories().stream().map((item) -> {
			CategoryProduct categoryProduct = new CategoryProduct();
			categoryProduct.setProductId(productId);
			categoryProduct.setCategoryId(item.getCategoryId());
			return categoryProduct;
		}).collect(Collectors.toList());
		categoryProductService.saveAll(categoryProducts);
		
		return result;
	}

	@Override
	@Transactional
	public void update(Product product) {
		Optional<Product> optional = repository.findById(product.getProductId());
		if (!optional.isPresent()) {
			throw new BizException("该商品已被删除");
		} else {
			Product productInDB = optional.get();
			product.setSaleVolume(productInDB.getSaleVolume());
			product.setCreateTime(productInDB.getCreateTime());
		}
		
		List<ProductImage> productImages = product.getProductImages();
		List<Category> categories = product.getCategories();
		
		// delete not existed old , add new productImage
		List<String> idsForDelete = productImages.stream().filter(item -> item.getProductImageId() != null)
				.map(ProductImage::getProductImageId).collect(Collectors.toList());
		if (idsForDelete.isEmpty()) {
			productImageService.deleteByProductId(product.getProductId());
		} else {
			productImageService.deleteByProductIdAndProductImageIdNotIn(product.getProductId(), idsForDelete);
		}
		productImages.stream().filter(item -> item.getProductImageId() == null).forEach((item) -> {
			item.setProductId(product.getProductId());
			item.setImageUrl(item.getImagePath());
			productImageService.saveOne(item);
		});
		
		// delete all relative categoryProduct, then create new categoryProduct
		categoryProductService.deleteByProductId(product.getProductId());
		List<CategoryProduct> categoryProducts = categories.stream().map(item -> {
			CategoryProduct categoryProduct = new CategoryProduct();
			categoryProduct.setCategoryId(item.getCategoryId());
			categoryProduct.setProductId(product.getProductId());
			return categoryProduct;
		}).collect(Collectors.toList());
		categoryProductService.saveAll(categoryProducts);
		
		repository.save(product);
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		productImageService.deleteByProductId(id);
		repository.deleteById(id);
	}

	@Override
	public Page<ExtProductView> findAll(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return repository.findByOrderByCreateTimeDesc(pageable);
	}

	@Override
	public ExtProductView findByProductId(String id) {
		return repository.findByProductId(id);
	}

    @Override
    public ProductView findProductById(String productId) {
        return repository.findDetailById(productId);
    }

    @Override
    public ProductWithSingleImage getProductWithSingleImage(String productId) {
        return repository.getProductWithSingleImage(productId);
    }

}
