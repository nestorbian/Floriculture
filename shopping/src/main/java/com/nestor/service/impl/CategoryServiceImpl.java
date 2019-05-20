package com.nestor.service.impl;

import java.math.BigDecimal;
import java.util.*;

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
import com.nestor.vo.CategoryPageView;
import com.nestor.vo.CategoryPageView.ProductView;
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

        if (!repository.findById(category.getCategoryId()).isPresent()) {
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

    @Override
    public List<CategoryPageView> findCategoryInHome() {
        List<Category> categories = repository.findByNeedShowInHomeTrueOrderByCreateTimeAsc();
        
        // assemble list to CategoryPageView class
        List<CategoryPageView> categoryPageViews = new ArrayList<>();
        assembleCategoryPageView(categoryPageViews, categories, 6);

        return categoryPageViews;
    }

    @Override
    public List<CategoryPageView> findAllCategory() {
        List<Category> categories = repository.findAll();
        
        // assemble list to CategoryPageView class
        List<CategoryPageView> categoryPageViews = new ArrayList<>();
        assembleCategoryPageView(categoryPageViews, categories, null);

        return categoryPageViews;
    }

    @Override
    public List<CategoryPageView> findById(String id) {
        Optional<Category> categoryOptional = repository.findById(id);
        if (!categoryOptional.isPresent()) {
            throw new BizException(String.format("该分类已被删除, 分类id：%s", id));
        }
        Category category = categoryOptional.get();

        List<CategoryPageView> categoryPageViews = new ArrayList<>();
        assembleCategoryPageView(categoryPageViews, Arrays.asList(category), null);

        return categoryPageViews;
    }

    // assemble list to CategoryPageView class
    private void assembleCategoryPageView(List<CategoryPageView> categoryPageViews, List<Category> categories, Integer pageSize) {
        categories.stream().forEach(category -> {
            List<Map<String, Object>> list = repository.callCategorySP(category.getCategoryId(), pageSize);
            
            CategoryPageView categoryInHomeView = new CategoryPageView();
            List<ProductView> products = new ArrayList<>();

            list.stream().forEach(map -> {
                if (categoryInHomeView.getCategoryId() == null) {
                    categoryInHomeView.setCategoryId((String) map.get("categoryId"));
                    categoryInHomeView.setCategoryName((String) map.get("categoryName"));
                    categoryInHomeView.setCategoryDescription((String) map.get("categoryDescription"));
                    categoryInHomeView.setImageUrl(baseImageUrl + (String) map.get("imageUrl"));
                }
                ProductView productView = categoryInHomeView.new ProductView();
                productView.setProductId((String) map.get("productId"));
                productView.setProductName((String) map.get("productName"));
                productView.setProductDescription((String) map.get("productDescription"));
                productView.setProductOriginalPrice((BigDecimal) map.get("productOriginalPrice"));
                productView.setProductDiscountPrice((BigDecimal) map.get("productDiscountPrice"));
                productView.setProductImageUrl(baseImageUrl + (String) map.get("productImageUrl"));
                products.add(productView);
            });

            categoryInHomeView.setProducts(products);
            categoryPageViews.add(categoryInHomeView);
        });
    }

}
