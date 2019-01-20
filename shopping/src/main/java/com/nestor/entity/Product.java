package com.nestor.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {
	
	@Id
	private String productId;
	private String productName;
	private String productDescription;
	private BigDecimal productOriginalPrice; // 原价
	private BigDecimal productDiscountPrice; // 折扣价
	private String flowerMaterial; // 花材
	private String productPackage; // 包装
	private String productScene; // 场景
	private String productDetail; // 图文详情
	@CreationTimestamp
	private LocalDateTime createTime;
	@UpdateTimestamp
	private LocalDateTime updateTime;
	
//	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
//	@JoinColumn(name="category_id", insertable = false, updatable = false)
//	private Category category;
	
	@OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private List<ProductImage> productImages;
	
}
