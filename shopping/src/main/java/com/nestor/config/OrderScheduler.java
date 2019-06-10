package com.nestor.config;

import com.nestor.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 处理超过一天未继续支付的待支付订单，将其关闭
 */
@Component
@Slf4j
public class OrderScheduler {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void closePendingPayOrder() {
        log.warn("开始处理过期的待支付订单..");
        orderService.updatePendingPayOrder();
        log.warn("结束处理过期的待支付订单..");
    }
}
