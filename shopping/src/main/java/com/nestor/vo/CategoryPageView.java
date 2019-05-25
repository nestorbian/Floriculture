package com.nestor.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * <p>小程序主页显示分类和商品的视图类</p>
 * @author Lenovo
 *
 */
@Data
public class CategoryPageView {

    private String categoryId;
    private String categoryName;
    private String categoryDescription;
    private String categoryImageUrl;
    private List<ProductView> products;
    
    @Data
    public class ProductView {
        private String productId;
        private String productName;
        private String productDescription;
        private BigDecimal productOriginalPrice; // 原价
        private BigDecimal productDiscountPrice; // 折扣价
        private String imageUrl;
    }

}
