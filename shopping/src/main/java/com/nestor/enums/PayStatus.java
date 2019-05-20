package com.nestor.enums;

/**
 * 支付状态
 */
public enum PayStatus {
    PENDING("待支付"), FAIL("支付失败"), SUCCESS("支付成功");

    private String desc;
    private PayStatus(String desc) {
        this.desc = desc;
    }
}
