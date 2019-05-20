package com.nestor.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nestor.entity.UserAddress;
import com.nestor.repository.UserAddressRepository;
import com.nestor.service.UserAddressService;

@Service
public class UserAddressServiceInpl implements UserAddressService {
    
    @Autowired
    private UserAddressRepository repository;

    @Override
    public void saveUserAddress(UserAddress userAddress) {
        repository.save(userAddress);
    }

    @Override
    public UserAddress getUserAddress(String openid) {
        Optional<UserAddress> optional = repository.findById(openid);
        if (!optional.isPresent()) {
            return null;
        }
        return optional.get();
    }

}
