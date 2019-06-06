package com.nestor.repository;

import com.nestor.entity.WxOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<WxOrder, String> {
    Page<WxOrder> findByOpenidAndOrderStatus(String openid, String orderStatus, Pageable pageable);

    @Query(value = "update wx_order set order_status = ?1 where order_status = 'PENDING_PAY' " +
            "and UNIX_TIMESTAMP(create_time) < UNIX_TIMESTAMP(now()) - 86400", nativeQuery = true)
    @Modifying
    void updatePendingPayOrder(String orderStatus);
}
