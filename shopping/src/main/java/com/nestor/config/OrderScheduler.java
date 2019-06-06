package com.nestor.config;

import com.nestor.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderScheduler {

    @Autowired
    private OrderRepository orderRepository;

    @Scheduled(cron = "* * 1 * * ?")
    public void closePendingPayOrder() {
        System.out.println("work it");
    }
}
