package com.nestor.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "product_image")
@Data
public class ProductImage {
	
	@Id
	private String productImageId;
	@Column(name = "product_id")
	private String productId;
	private String productImagePath;
	private String productImageUrl;
	@CreationTimestamp
	private LocalDateTime createTime;
	@UpdateTimestamp
	private LocalDateTime updateTime;
	
}
