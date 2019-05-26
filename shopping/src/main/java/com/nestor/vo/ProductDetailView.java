package com.nestor.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDetailView {
    private String productId;
    private String productName;
    private String productDescription;
    private BigDecimal productOriginalPrice; // 原价
    private BigDecimal productDiscountPrice; // 折扣价
    private Long productStock; // 库存
    private String flowerMaterial; // 花材
    private String productPackage; // 包装
    private String productScene; // 场景
    private String distribution; // 配送
    private Long saleVolume; // 销量
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<ProductView.productImageView> productImages;
    private List<CommentView> comments;
}
