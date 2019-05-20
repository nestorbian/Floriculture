package com.nestor.service;

import com.nestor.entity.UserAddress;

public interface UserAddressService {

    void saveUserAddress(UserAddress userAddress);

    UserAddress getUserAddress(String openid);
    
}
