package com.nestor.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.jws.soap.SOAPBinding;

import org.aspectj.weaver.ast.And;
import org.hibernate.query.criteria.internal.expression.function.SubstringFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.nestor.Constants;
import com.nestor.common.LogHttpInfo;
import com.nestor.entity.Product;
import com.nestor.entity.Result;
import com.nestor.entity.WxUser;
import com.nestor.service.WxLoginService;

import antlr.StringUtils;
import antlr.Utils;

@RestController
@RequestMapping("/WxLoginController")
public class WxLoginController {	
	@Autowired
	private WxLoginService wxService;
	
    private String appid="wxa7025e69b9d8b749";
    private String appSecret="abd4247f4848c338717e0a556c3c419e";
	 
	/**
	 * 获取获取的openid、session_key、unionid和thirdSession
	 * @param loginUser
	 * @return
	 */
	@GetMapping(path = "/loginUser")
	@LogHttpInfo
	public String loginUser(String code) {

		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+ appSecret+"&js_code="+code+"&grant_type=authorization_code";
		
		//REST将资源的状态以最合适客户端或服务端的形式从服务器端转移到客户端（相反亦可）
		RestTemplate restTemplate =new RestTemplate();
		ResponseEntity<String> res = restTemplate.exchange(url , HttpMethod.GET ,null,String.class);
		//根据返回值进行后续操作
		if(res != null && res.getStatusCode() == HttpStatus.OK) {
			String sessionData = res.getBody();
			//谷歌爸爸提供用来在java对象和Json数据之间进行映射的类库
			Gson gson =new Gson();
			//解析从微信服务器获取的openid、session_key、unionid
			WxUser wxSession = gson.fromJson(sessionData , WxUser.class);
			//3rd_session = openid前十六位 + session_key前十六位
			wxSession.setThirdSession(wxSession.getOpenid().substring(0,16) + wxSession.getSession_key().substring(0,16));
			//将实体写入数据库
			if(wxService.existsById(wxSession.getOpenid())) {
				wxService.updateOne(wxSession.getOpenid(), wxSession.getSession_key(), wxSession.getThirdSession(),wxSession.getUnionid());
			}else {
				wxService.save(wxSession);
			}
			//返回 3rd_session
			return wxSession.getThirdSession();
		}
		
        return null;
	}
		
	/*
	 * 用来获取用户个人信息	 *
	 */
	@GetMapping(path = "/getUInfo")
	@LogHttpInfo
	public WxUser getUInfo(String thirdSession) {
		ArrayList<WxUser> wxUsers =wxService.findByThirdSession(thirdSession);
		if(wxUsers ==null || wxUsers.size()==0) {
			return null;
		}
        return wxUsers.get(0);
	}
	/*
	 * 新增或更新用户个人信息	 *
	 */
	@PostMapping(path = "/setUInfo")
	@LogHttpInfo
	public String setUInfo( WxUser wxUser) {
		if(wxUser == null) {
			return null;
		}
		
		wxService.updateBy3rd(wxUser.getAvatarurl(),wxUser.getNickname(),wxUser.getTelnum(),wxUser.getGender(),wxUser.getLocation(),wxUser.getBirthday(),wxUser.getProvince(),wxUser.getCity(),wxUser.getCountry(),wxUser.getThirdSession());
		return "ok";
	}
}
