package com.nestor.service;

import com.nestor.entity.WxUser;


public interface WxLoginService {
	

	public String loginUser(String code);
		

	public WxUser getUInfo(String thirdSession) ;

	public String setUInfo( WxUser wxUser);
	
	public String updateNick(WxUser wxUser);
	
	public String updateNickAva(String avatarurl,String nickname,String thirdSession);

}
