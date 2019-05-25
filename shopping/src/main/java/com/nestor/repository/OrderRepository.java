package com.nestor.repository;

import com.nestor.entity.WxOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<WxOrder, String> {
    Page<WxOrder> findByOpenidAndOrderStatus(String openid, String orderStatus, Pageable pageable);
}
