package com.nestor.dto;

import lombok.Data;

@Data
public class SimpleOrder {
    private String openid;
    private String addressId; // 详细地址
    private String productId; // 商品id
    private Integer buyAmount; // 购买数量
    private String expectedDeliveryTime; // 期望配送时间
    private String label; // 标签
    private String remark; // 备注
    private String leaveMessage; // 留言
}
