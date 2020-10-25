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

    
    /**
     * 每一分钟处理一次
     *
     * @param 
     * @return void
     * @date : 2020/10/24 22:16
     * @author : Nestor.Bian
     * @since : 1.0
     */
    @Scheduled(cron = "* 0/1 * * * ?")
    public void closePendingPayOrder() {
        log.warn("开始处理过期的待支付订单..");
        orderService.updatePendingPayOrder();
        log.warn("结束处理过期的待支付订单..");
    }
}
