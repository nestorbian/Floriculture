package com.nestor.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
public class ProductImage {
	
	@Id
	private String product_image_id;
	private String product_id;
	private String product_image_path;
	private String product_image_url;
	private LocalDateTime create_time;
	@UpdateTimestamp
	private LocalDateTime update_time;
	
}
