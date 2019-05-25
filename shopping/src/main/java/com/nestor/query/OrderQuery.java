package com.nestor.query;

import lombok.Data;

@Data
public class OrderQuery {
    private String orderNumber;
    private String orderStatus;
    private int pageNumber;
    private int pageSize;
}
