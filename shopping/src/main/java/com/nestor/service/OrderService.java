package com.nestor.service;

import com.lly835.bestpay.model.PayResponse;
import com.nestor.dto.SimpleOrder;
import com.nestor.entity.Order;

public interface OrderService {
	PayResponse generateOrder(SimpleOrder simpleOrder);
	void handleCallback(String callBackBody);
}
