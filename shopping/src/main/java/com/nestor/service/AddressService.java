package com.nestor.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nestor.entity.WxAddress;

public interface AddressService  {
	/*
	 * 用来获取用户个人信息	 *
	 */
	public String setAddress(String thirdSession,String username,String telnum,String location,String detail_add,String id) ;
	
	/*
	 * 用来获取单个地址信息	 *
	 */
	public WxAddress getAddress(String id) ;
	
	/*
	 * 用来获取全部地址信息	 *
	 */
	public ArrayList<WxAddress> getAllAddress(String thirdSession);
	
	/*
	 * 用来获取单个地址信息	 *
	 */
	public String delAddress(String id);
}
