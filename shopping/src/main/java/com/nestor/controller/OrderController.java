package com.nestor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.common.LogHttpInfo;
import com.nestor.entity.OrderParam;
import com.nestor.entity.Result;
import com.nestor.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService service;
	
	@PostMapping(path = "/generate-order")
	@LogHttpInfo
	public Result<Boolean> generateOrder(@RequestBody List<OrderParam> orderParams) {
		service.generateOrder(orderParams);
		return new Result<>(true);
	}
}
