package com.nestor.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@Table(name = "order_detail")
@DynamicUpdate
@Data
public class OrderDetail {
	@Id
	@Column(name = "order_detail_id")
	private String orderDetailId;
	private String productName;
	private String productDescription;
	private String productIcon;
	private BigDecimal productPrice;
	private int productQuantity;
	@Column(name = "order_master_id")
	private String orderMasterId;
	private LocalDateTime createTime;
}
