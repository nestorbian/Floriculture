package com.nestor.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "category_product")
@IdClass(value = CategoryProduct.class)
public class CategoryProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6360913880567993939L;
	@Id
	@Column(name = "category_id")
	private String categoryId;
	@Id
	@Column(name = "product_id")
	private String productId;
}
