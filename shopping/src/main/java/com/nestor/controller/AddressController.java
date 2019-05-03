package com.nestor.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.common.LogHttpInfo;
import com.nestor.entity.WxAddress;
import com.nestor.service.AddressService;

@RestController
@RequestMapping("/addressController")
public class AddressController {
    @Autowired
    private AddressService addSce;

    /*
     * 用来获取用户个人信息 *
     */
    @GetMapping(path = "/setAddress")
    @LogHttpInfo
    public String setAddress(String thirdSession, String username, String telnum, String location, String detail_add,
            String id) {
        return addSce.setAddress(thirdSession, username, telnum, location, detail_add, id);
    }

    /*
     * 用来获取单个地址信息 *
     */
    @GetMapping(path = "/getAddress")
    @LogHttpInfo
    public Optional<WxAddress> getAddress(String id) {
        return addSce.getAddress(id);
    }

    /*
     * 用来获取全部地址信息 *
     */
    @GetMapping(path = "/getAllAddress")
    @LogHttpInfo
    public ArrayList<WxAddress> getAllAddress(String thirdSession) {
    	System.out.println(thirdSession);
        return addSce.getAllAddress(thirdSession);
    }

    /*
     * 用来获取单个地址信息 *
     */
    @GetMapping(path = "/delAddress")
    @LogHttpInfo
    public String delAddress(String id) {
        return addSce.delAddress(id);
    }
}
