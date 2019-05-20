package com.nestor.controller;

import java.util.List;

import com.nestor.common.LoginExpiredException;
import com.nestor.dto.SimpleOrder;
import com.nestor.entity.WxUser;
import com.nestor.service.WxLoginService;
import com.nestor.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.WxPayServiceImpl;
import com.nestor.common.LogHttpInfo;
import com.nestor.entity.Order;
import com.nestor.entity.Result;
import com.nestor.service.OrderService;
import com.nestor.util.IdUtil;
import com.nestor.util.JacksonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>订单、支付</p>
 * @author bzy
 *
 */
@Controller
@Slf4j
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private WxLoginService wxLoginService;

    @Autowired
    private WxPayServiceImpl wxPayService;

    @GetMapping(path = "/order/test")
    @LogHttpInfo
    @ResponseBody
    public Result<PayResponse> test() {
        PayRequest payRequest = new PayRequest();
        String orderId = IdUtil.generateId();
        log.info("orderId: {}", orderId);
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
    @ResponseBody
    public Result<RefundResponse> refund(@RequestParam(name = "orderId") String orderId) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderId);
        refundRequest.setOrderAmount(0.01);
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        return new Result<>(wxPayService.refund(refundRequest));
    }

    /**
     * 微信支付回调通知
     * @param body
     * @return
     */
    @PostMapping(path = "/pay/callback")
    @LogHttpInfo
    public String payCallBack(@RequestBody String body) {
        service.handleCallback(body);
        return "success";
    }
    
    /**
     * <p>生成待支付订单，去调起微信支付接口，返回preOrderId</p>
     * @param simpleOrder
     * @return
     */
    @PostMapping(path = "/orders")
    @LogHttpInfo
    @ResponseBody
    public Result<PayResponse> generateOrder(@RequestHeader(name = "authorization") String token, @RequestBody SimpleOrder simpleOrder) {
        CheckUtil.notEmpty(token, "token不能为空");
        WxUser wxUser = wxLoginService.getUInfo(token);
        if (ObjectUtils.isEmpty(wxUser)) {
            throw new LoginExpiredException("请先登录");
        }
        simpleOrder.setOpenid(wxUser.getOpenid());
        // simpleOrder 参数验证
        CheckUtil.notEmpty(simpleOrder.getAddressId(), "address id 不能为为空");
        CheckUtil.notEmpty(simpleOrder.getProductId(), "product id 不能为空");
        CheckUtil.notLessThanEqualZero(simpleOrder.getBuyAmount(), "购买数量不能小于1");
        CheckUtil.notEmpty(simpleOrder.getExpectedDeliveryTime(), "配送时间不能为空");

        PayResponse payResponse = service.generateOrder(simpleOrder);
        return new Result<>(payResponse);
    }

}
