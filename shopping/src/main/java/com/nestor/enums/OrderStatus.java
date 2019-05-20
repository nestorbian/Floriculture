package com.nestor.enums;

/**
 * 订单状态
 */
public enum OrderStatus {
    PENDING_PAY("待支付"), PENDING_DELIVERY("待发货"), PENDING_RECEIVE("待收货"), PENDING_COMMENT("待评价"), CLOSE("交易关闭"), FINISH("交易完成");
    private String desc;
    private OrderStatus(String desc) {
        this.desc = desc;
    }
}
