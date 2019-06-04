package com.nestor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.common.LogHttpInfo;
import com.nestor.entity.WxUser;
import com.nestor.service.WxLoginService;


@RestController
@RequestMapping("/WxLoginController")
public class WxLoginController {	
	@Autowired
	private WxLoginService wxService;
	

	/**
	 * 获取获取的openid、session_key、unionid和thirdSession
	 * @param loginUser
	 * @return
	 */
	@GetMapping(path = "/loginUser")
	@LogHttpInfo
	public String loginUser(String code) {
        return wxService.loginUser(code);
	}
		
	/*
	 * 用来获取用户个人信息	 *
	 */
	@GetMapping(path = "/getUInfo")
	@LogHttpInfo
	public WxUser getUInfo(String thirdSession) {
		return wxService.getUInfo(thirdSession);
	}
	/*
	 * 新增或更新用户个人信息	 *
	 */
	@PostMapping(path = "/setUInfo")
	@LogHttpInfo
	public String setUInfo( WxUser wxUser) {
		return wxService.setUInfo(wxUser);
	}
	
	/*
	 * 登录时简略更新“昵称 头像“	 *
	 */
	@GetMapping(path = "/updateNick")
	@LogHttpInfo
	public String updateNick(WxUser wxUser,String sign) {
		//新增用户为“0”，老用户为“1”
		if(sign.equals("new")) {
			return wxService.updateNick(wxUser);			
		}else if(sign.equals("old")){
			return wxService.updateNickAva(wxUser.getAvatarurl(), wxUser.getNickname(), wxUser.getThirdSession());
		}else {
			return "error";
		}	
	}
}
