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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@Table(name = "order_master")
@DynamicUpdate
@Data
public class OrderMaster {
	
	@Id
	@Column(name = "order_master_id")
	private String orderMasterId;
	private BigDecimal totalAmount;
	private BigDecimal discountAmount;
	private BigDecimal payAmount;
	private PayStatus payStatus;
	private String merchantName;
	private String merchantAddress;
	private String buyerOpenid;
	private String buyerName;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private LocalDateTime payTime;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="order_master_id", referencedColumnName = "order_master_id", insertable = false, updatable = false)
	private List<OrderDetail> orderDetails;
}
