package com.nestor.vo;

import lombok.Data;

@Data
public class CountOrderView {
    private long pendingPay;
    private long pendingDelivery;
    private long pendingReceive;
    private long pendingComment;
    private long pendingRefund;
}
