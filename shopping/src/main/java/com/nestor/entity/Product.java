package com.nestor.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@Table(name = "product")
@Data
@DynamicUpdate
public class Product {
	@Id
	private String productId;
	private String productName;
	private String productDescription;
	private String productIcon;
	private BigDecimal productPrice;
	@Column(name = "category_id")
	private String categoryId;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	
//	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
//	@JoinColumn(name="category_id", insertable = false, updatable = false)
//	private Category category;
}
