package com.nestor.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "product_image")
@Data
public class ProductImage {
	
	@Id
	@GeneratedValue(generator = "system_generator")
	@GenericGenerator(name = "system_generator", strategy = "uuid")
	private String productImageId;
	@Column(name = "product_id")
	private String productId;
	private String imagePath;
	private String imageUrl;
	@CreationTimestamp
	private LocalDateTime createTime;
	@UpdateTimestamp
	private LocalDateTime updateTime;
	
}
