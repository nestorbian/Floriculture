package com.nestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nestor.entity.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, String> {

}
