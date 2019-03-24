package com.nestor.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.common.LogHttpInfo;
import com.nestor.entity.WxAddress;
import com.nestor.entity.WxUser;
import com.nestor.service.AddressService;
import com.nestor.service.WxLoginService;
import com.nestor.util.IdUtil;

@RestController
@RequestMapping("/addressController")
public class AddressController {
	@Autowired
	private AddressService addSce;
	@Autowired
	private WxLoginService wxLinSce;

	/*
	 * 用来获取用户个人信息	 *
	 */
	@GetMapping(path = "/setAddress")
	@LogHttpInfo
	public String setAddress(String thirdSession,String username,String telnum,String location,String detail_add,String id) {
		WxAddress wxAddress = new WxAddress();
		wxAddress.setUsername(username);
		wxAddress.setTelnum(telnum);
		wxAddress.setLocation(location);
		wxAddress.setDetail_add(detail_add);
		ArrayList<String> openIdList = wxLinSce.findOpenidBy3rd(thirdSession);
		//如果地址信息编号为空，则赋值给他
		if(id.equals("1")) {
			id = IdUtil.generateId();
		}
		wxAddress.setId(id);
		if(openIdList == null || openIdList.size()==0) {
			return "保存失败";
		}
		String openid =   openIdList.get(0);
		wxAddress.setOpenid(openid);
		addSce.save(wxAddress);
		return "保存成功";
	}
	
	/*
	 * 用来获取单个地址信息	 *
	 */
	@GetMapping(path = "/getAddress")
	@LogHttpInfo
	public Optional<WxAddress> getAddress(String id) {
		if(id == null ) {return null ;}
		Optional<WxAddress> wxAddress = addSce.findById(id);
		return wxAddress;
	}
	
	/*
	 * 用来获取全部地址信息	 *
	 */
	@GetMapping(path = "/getAllAddress")
	@LogHttpInfo
	public ArrayList<WxAddress> getAllAddress(String thirdSession) {
		ArrayList<String> openIdList = wxLinSce.findOpenidBy3rd(thirdSession);
		String openid =   openIdList.get(0);
		ArrayList<WxAddress> wxAddress = addSce.findAddressList(openid);
		return wxAddress;
	}
}
