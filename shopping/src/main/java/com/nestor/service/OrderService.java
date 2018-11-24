package com.nestor.service;

import java.util.List;

import com.nestor.entity.OrderMaster;
import com.nestor.entity.OrderParam;

public interface OrderService {
	public void generateOrder(List<OrderParam> orderParams);
	
	public List<OrderMaster> findAll();
	
	public OrderMaster findById();
}
