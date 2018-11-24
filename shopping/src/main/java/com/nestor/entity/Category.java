package com.nestor.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@Table(name = "category")
@DynamicUpdate
@Data
public class Category {
	@Id
	@Column(name = "category_id")
	private String categoryId;
	@Column(name = "category_name", unique = true)
	private String categoryName;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="category_id", insertable = false, updatable = false)
	private List<Product> productList;
}
