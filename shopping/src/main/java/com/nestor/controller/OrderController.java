package com.nestor.controller;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.WxPayServiceImpl;
import com.nestor.common.LogHttpInfo;
import com.nestor.common.LoginExpiredException;
import com.nestor.dto.SimpleOrder;
import com.nestor.entity.Result;
import com.nestor.entity.WxOrder;
import com.nestor.entity.WxUser;
import com.nestor.enums.OrderStatus;
import com.nestor.query.OrderQuery;
import com.nestor.service.OrderService;
import com.nestor.service.WxLoginService;
import com.nestor.util.CheckUtil;
import com.nestor.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping(path = "/order/test")
//    @LogHttpInfo
//    @ResponseBody
//    public Result<PayResponse> test() {
//        PayRequest payRequest = new PayRequest();
//        String orderId = IdUtil.generateId();
//        log.info("orderId: {}", orderId);
//        payRequest.setOpenid("omKUZ49PArL_lKiXyHpS2O3v6LqM");
//        payRequest.setOrderAmount(0.01);
//        payRequest.setOrderId(orderId);
//        payRequest.setOrderName("南雅花艺店订单");
//        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
//
//        PayResponse payResponse = wxPayService.pay(payRequest);
//        payResponse.setOrderId(orderId);
//        return new Result<>(payResponse);
//    }
    
//    @GetMapping(path = "/refund/test")
//    @LogHttpInfo
//    @ResponseBody
//    public Result<RefundResponse> refund(@RequestParam(name = "orderId") String orderId) {
//        RefundRequest refundRequest = new RefundRequest();
//        refundRequest.setOrderId(orderId);
//        refundRequest.setOrderAmount(0.01);
//        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
//        return new Result<>(wxPayService.refund(refundRequest));
//    }

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
        WxUser wxUser = checkLogin(token);
        simpleOrder.setOpenid(wxUser.getOpenid());
        // simpleOrder 参数验证
        CheckUtil.notEmpty(simpleOrder.getAddressId(), "address id 不能为为空");
        CheckUtil.notEmpty(simpleOrder.getProductId(), "product id 不能为空");
        CheckUtil.notLessThanEqualZero(simpleOrder.getBuyAmount(), "购买数量不能小于1");
        CheckUtil.notEmpty(simpleOrder.getExpectedDeliveryTime(), "配送时间不能为空");

        PayResponse payResponse = service.generateOrder(simpleOrder);
        return new Result<>(payResponse);
    }

    /**
     * 待支付状态的订单，进行继续支付
     * @param token
     * @param id
     * @return
     */
    @GetMapping(path = "/orders/continue-pay")
    @LogHttpInfo
    @ResponseBody
    public Result<?> continuePay(@RequestHeader(name = "authorization") String token, @RequestParam(name = "id") String id) {
        WxUser wxUser = checkLogin(token);
        return new Result<>(service.continuePay(wxUser.getOpenid(), id));
    }

    /**
     * 更新订单状态，设置物流信息
     * @param order
     * @return
     */
    @PutMapping(path = "/admin/orders")
    @LogHttpInfo
    @ResponseBody
    public Result<Boolean> updateTrackingNumber(@RequestBody WxOrder order) {
        CheckUtil.notEmpty(order.getOrderId(), "订单id不能为空");
        CheckUtil.notEmpty(order.getTrackingNumber(), "快递单号不能为空");
        service.updateTrackingNumber(order);
        return new Result<>(true);
    }

    /**
     * 确认收货
     * @param token
     * @param id
     * @return
     */
    @PutMapping(path = "/orders/confirm-product")
    @LogHttpInfo
    @ResponseBody
    public Result<Boolean> confirmProduct(@RequestHeader(name = "authorization") String token, @RequestParam(name = "id") String id) {
        checkLogin(token);
        service.updateOrderStatus(id, OrderStatus.PENDING_COMMENT);
        return new Result<>(true);
    }

    /**
     * design for admin ui
     * 确认收货
     * @param id
     * @return
     */
    @PutMapping(path = "/admin/orders/confirm-product")
    @LogHttpInfo
    @ResponseBody
    public Result<Boolean> confirmProduct(@RequestParam(name = "id") String id) {
        service.updateOrderStatus(id, OrderStatus.PENDING_COMMENT);
        return new Result<>(true);
    }


    /**
     * 取消订单
     * @param token
     * @param id
     * @return
     */
    @PutMapping(path = "/orders/cancel-order")
    @LogHttpInfo
    @ResponseBody
    public Result<Boolean> cancelOrder(@RequestHeader(name = "authorization") String token, @RequestParam(name = "id") String id) {
        checkLogin(token);
        service.updateOrderStatus(id, OrderStatus.CLOSE);
        return new Result<>(true);
    }

    /**
     * 申请退款
     * @param token
     * @param id
     * @return
     */
    @PutMapping(path = "/orders/apply-refund")
    @LogHttpInfo
    @ResponseBody
    public Result<Boolean> applyRefund(@RequestHeader(name = "authorization") String token, @RequestParam(name = "id") String id) {
        checkLogin(token);
        service.updateOrderStatus(id, OrderStatus.PENDING_REFUND);
        return new Result<>(true);
    }

    /**
     * design for admin ui
     * 确认退款
     * @param id
     * @return
     */
    @PutMapping(path = "/admin/orders/confirm-refund")
    @LogHttpInfo
    @ResponseBody
    public Result<Boolean> confirmRefund(@RequestParam(name = "id") String id) {
        service.updateOrderStatus(id, OrderStatus.FINISH_REFUND);
        return new Result<>(true);
    }

    /**
     * 根据订单状态获取订单列表
     * @param token
     * @param orderStatus
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(path = "/orders/{orderStatus}")
    @LogHttpInfo
    @ResponseBody
    public Result<?> listOrderByOrderStatus(@RequestHeader(name = "authorization") String token, @PathVariable String orderStatus,
                                            @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
        WxUser wxUser = checkLogin(token);
        CheckUtil.notNull(OrderStatus.parse(orderStatus), "订单状态不合法");
        CheckUtil.notLessThanEqualZero(pageNumber, "当前页不合法");
        CheckUtil.notLessThanEqualZero(pageSize, "分页数不合法");
        return new Result<>(service.listOrderByOrderStatus(wxUser.getOpenid(), OrderStatus.parse(orderStatus), pageNumber, pageSize));
    }

    @GetMapping(path = "/orders/detail")
    @LogHttpInfo
    @ResponseBody
    public Result<?> getOrderById(@RequestHeader(name = "authorization") String token, @RequestParam(name = "id") String id) {
        WxUser wxUser = checkLogin(token);

        return new Result<>(service.getOrderById(wxUser.getOpenid(), id));
    }

    /**
     * design for admin ui
     * 根据条件分页获取订单
     * @param orderQuery
     * @return
     */
    @GetMapping(path = "/admin/orders")
    @LogHttpInfo
    @ResponseBody
    public Result<?> listOrderByOrderQuery(@ModelAttribute OrderQuery orderQuery) {
        if (!StringUtils.isEmpty(orderQuery.getOrderStatus())) {
            CheckUtil.notNull(OrderStatus.parse(orderQuery.getOrderStatus()), "订单状态不合法");
        }
        CheckUtil.notLessThanEqualZero(orderQuery.getPageNumber(), "当前页不合法");
        CheckUtil.notLessThanEqualZero(orderQuery.getPageSize(), "分页数不合法");
        return new Result<>(service.listOrderByOrderQuery(orderQuery));
    }

    /**
     * 验证用户是否已登录
     * @param token
     * @return
     */
    private WxUser checkLogin(String token) {
        CheckUtil.notEmpty(token, "token不能为空");
        WxUser wxUser = wxLoginService.getUInfo(token);
        if (ObjectUtils.isEmpty(wxUser)) {
            throw new LoginExpiredException("请先登录");
        }
        return wxUser;
    }

}
