package com.nestor.service;

import com.lly835.bestpay.model.PayResponse;
import com.nestor.dto.SimpleOrder;
import com.nestor.entity.WxOrder;
import com.nestor.enums.OrderStatus;
import com.nestor.query.OrderQuery;
import org.springframework.data.domain.Page;

public interface OrderService {
	PayResponse generateOrder(SimpleOrder simpleOrder);
	void handleCallback(String callBackBody);
	PayResponse continuePay(String openid, String id);
	void updateTrackingNumber(WxOrder order);
	void updateOrderStatus(String id, OrderStatus orderStatus);
	Page<WxOrder> listOrderByOrderStatus(String openid, OrderStatus orderStatus, int pageNumber, int pageSize);
	Page<WxOrder> listOrderByOrderQuery(OrderQuery orderQuery);
	WxOrder getOrderById(String openid, String orderId);
}
