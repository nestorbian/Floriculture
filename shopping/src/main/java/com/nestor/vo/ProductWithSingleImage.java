package com.nestor.vo;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;

public interface ProductWithSingleImage {
    String getProductId();

    String getProductName();

    String getProductDescription();

    BigDecimal getProductOriginalPrice(); // 原价

    BigDecimal getProductDiscountPrice(); // 折扣价

    Long getProductStock(); // 库存

    String getFlowerMaterial(); // 花材

    String getProductPackage(); // 包装

    String getProductScene(); // 场景

    String getDistribution(); // 配送

    Long getSaleVolume(); // 销量

    @Value("#{@viewConvertion.getBaseImageUrl() + target.imageUrl}")
    String getImageUrl();
}
