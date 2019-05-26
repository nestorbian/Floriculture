package com.nestor.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {
	
	@Id
	@Column(name = "product_id")
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
	@CreationTimestamp
	private LocalDateTime createTime;
	@UpdateTimestamp
	private LocalDateTime updateTime;
	
	@OneToMany(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private List<ProductImage> productImages;
	
	@ManyToMany(mappedBy = "productList")
	private List<Category> categories;

	@OneToMany(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private List<Comment> comments;
}
