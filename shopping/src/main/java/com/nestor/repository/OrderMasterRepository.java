package com.nestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nestor.entity.OrderMaster;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

}
