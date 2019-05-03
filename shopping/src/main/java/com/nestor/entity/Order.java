package com.nestor.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "order")
@Data
public class Order {
    @Id
    @GenericGenerator(name = "generateOrderId", strategy = "uuid")
    @GeneratedValue(generator = "generateOrderId")
    private String orderId;
    private String orderNumber; // 订单编号
    private BigDecimal payAmount; // 支付金额
    private String payStatus; // 支付状态
    private String orderStatus; // 订单状态
    private String area; // 地区
    private String addressDetail; // 详细地址
    private String username; // 用户名
    private String phoneNumber; // 手机号码
    private String productId; // 外键，商品id
    private String productName; // 商品名称
    private String productDescription; // 商品描述
    private BigDecimal productPrice; // 商品价格
    private Long productStock; // 库存
    private String productImageUrl; // 商品图片
    private Integer buyAmount; // 购买数量 
    private LocalDate expectedDeliveryDate; // 期望配送日期
    private String label; // 标签
    private String remark; // 备注
    private String leaveMessage; // 留言
    private String trackingNumber; // 快递单号
    private LocalDateTime orderTime; // 下单时间
    private LocalDateTime payTime; // 支付时间
    @CreationTimestamp
    private LocalDateTime createTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;
}
