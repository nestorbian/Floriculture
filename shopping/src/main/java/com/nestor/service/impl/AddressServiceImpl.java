package com.nestor.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nestor.common.BizException;
import com.nestor.entity.WxAddress;
import com.nestor.repository.AddressRepository;
import com.nestor.repository.WxLoginRepository;
import com.nestor.service.AddressService;
import com.nestor.util.IdUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService  {
	@Autowired
	private AddressRepository addR;
	@Autowired
	private WxLoginRepository wxR;
	
	/*
	 * 用来获取用户个人信息	 *
	 */
	public String setAddress(String thirdSession,String username,String telnum,String location,String detail_add,String id) {
		WxAddress wxAddress = new WxAddress();
		wxAddress.setUsername(username);
		wxAddress.setTelnum(telnum);
		wxAddress.setLocation(location);
		wxAddress.setDetailAdd(detail_add);
		wxAddress.setCity("");
		ArrayList<String> openIdList = wxR.findOpenidBy3rd(thirdSession);
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
		addR.save(wxAddress);
		return "保存成功";
	}
	
	/*
	 * 用来获取单个地址信息	 *
	 */
	public WxAddress getAddress(String id) {
		Optional<WxAddress> wxAddress = addR.findById(id);
		if (!wxAddress.isPresent()) {
		    log.warn("该地址已被删除，id为{}", id);
		    throw new BizException("该地址已被删除");
		}
		return wxAddress.get();
	}
	
	/*
	 * 用来获取全部地址信息	 *
	 */
	public ArrayList<WxAddress> getAllAddress(String thirdSession) {
		ArrayList<String> openIdList = wxR.findOpenidBy3rd(thirdSession);
		String openid =   openIdList.get(0);
		ArrayList<WxAddress> wxAddress = addR.findAddressList(openid);
		return wxAddress;
	}
	
	/*
	 * 用来获取单个地址信息	 *
	 */
	public String delAddress(String id) {
		if(id == null ) {return null ;}
		addR.deleteById(id);
		return "ok";
	}
	
}
