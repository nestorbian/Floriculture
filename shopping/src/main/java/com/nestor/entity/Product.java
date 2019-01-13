package com.nestor.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private BigDecimal productOriginalPrice;
	private BigDecimal productDiscountPrice;
	private String flowerMaterial;
	private String productPackage;
	private String productScene;
	private String productDetail;
	private LocalDateTime createTime;
	@UpdateTimestamp
	private LocalDateTime updateTime;
	
//	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
//	@JoinColumn(name="category_id", insertable = false, updatable = false)
//	private Category category;
	
}
