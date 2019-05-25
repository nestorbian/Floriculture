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

    public static PayStatus parse(String value) {
        for (PayStatus payStatus : PayStatus.values()) {
            if (payStatus.toString().equals(value)) {
                return payStatus;
            }
        }

        return null;
    }

    public String getDesc() {
        return desc;
    }
}
