package com.nestor.enums;

/**
 * 订单状态
 */
public enum OrderStatus {
    PENDING_PAY("待支付"), PENDING_DELIVERY("待发货"), PENDING_RECEIVE("待收货"),
    PENDING_COMMENT("待评价"), CLOSE("交易关闭"), FINISH("交易完成"),
    PENDING_REFUND("待退款"), FINISH_REFUND("完成退款");
    private String desc;
    private OrderStatus(String desc) {
        this.desc = desc;
    }

    public static OrderStatus parse(String value) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.toString().equals(value)) {
                return orderStatus;
            }
        }

        return null;
    }

    public String getDesc() {
        return desc;
    }
}
