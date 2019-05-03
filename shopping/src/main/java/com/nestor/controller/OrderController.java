package com.nestor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.WxPayServiceImpl;
import com.nestor.common.LogHttpInfo;
import com.nestor.entity.OrderParam;
import com.nestor.entity.Result;
import com.nestor.service.OrderService;
import com.nestor.util.IdUtil;
import com.nestor.util.JacksonUtil;

@RestController
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private WxPayServiceImpl wxPayService;

    @PostMapping(path = "/generate-order")
    @LogHttpInfo
    public Result<Boolean> generateOrder(@RequestBody List<OrderParam> orderParams) {
        service.generateOrder(orderParams);
        return new Result<>(true);
    }

    @GetMapping(path = "/order/test")
    @LogHttpInfo
    public Result<PayResponse> test() {
        PayRequest payRequest = new PayRequest();
        String orderId = IdUtil.generateId();
        payRequest.setOpenid("omKUZ49PArL_lKiXyHpS2O3v6LqM");
        payRequest.setOrderAmount(0.01);
        payRequest.setOrderId(orderId);
        payRequest.setOrderName("南雅花艺店订单");
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        PayResponse payResponse = wxPayService.pay(payRequest);
        payResponse.setOrderId(orderId);
        return new Result<>(payResponse);
    }
    
    @GetMapping(path = "/refund/test")
    @LogHttpInfo
    public Result<RefundResponse> refund(@RequestParam(name = "orderId") String orderId) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderId);
        refundRequest.setOrderAmount(0.01);
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        return new Result<>(wxPayService.refund(refundRequest));
    }
    
    @PostMapping(path = "/pay/callback")
    @LogHttpInfo
    public void payCallBack(@RequestBody String body) {
        System.out.printf("callback body is %s\n", body);
        PayResponse payResponse = wxPayService.asyncNotify(body);
        System.out.printf("callback payResponse is {}", JacksonUtil.object2JsonStr(payResponse));
    }
}
