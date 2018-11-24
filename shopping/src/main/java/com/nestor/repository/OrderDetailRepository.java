package com.nestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nestor.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

}
