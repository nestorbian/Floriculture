package com.nestor.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.nestor.entity.WxUser;

public interface WxLoginRepository extends JpaRepository<WxUser, String>{
	//原生SQL实现更新方法接口
	@Transactional
	@Query(value = "update t_wx_user set session_key=?2,third_session=?3,unionid=?4 where openid=?1 ", nativeQuery = true)  
	@Modifying  
	public void updateOne(String openid,String session_key,String setThirdSession,String unionid); 

	public ArrayList<WxUser> findByThirdSession(String thirdSession);
	
	//原生SQL实现更新方法接口
	//初始化"头像 昵称"
	@Transactional
	@Query(value = "update t_wx_user set avatarurl=?1,nickname= ?2 where third_session=?3 ", nativeQuery = true)  
	@Modifying  
	public void updateNickAva(String avatarurl,String nickname,String thirdSession); 
	
	//原生SQL实现更新方法接口
		//初始化个人信息
		@Transactional
		@Query(value = "update t_wx_user set avatarurl=?1,nickname= ?2,gender= ?3,province= ?4,city= ?5,country=?6 ,language=?7 where third_session=?8 ", nativeQuery = true)  
		@Modifying  
		public void updateNick(String avatarurl,String nickname  ,String gender,String province ,String city,String country,String language,String thirdSession); 
	
	//原生SQL实现更新方法接口
	@Transactional
	@Query(value = "select openid from  t_wx_user  where third_session=?1 ", nativeQuery = true)  
	@Modifying  
	public ArrayList<String> findOpenidBy3rd(String thirdSession); 
	

	//原生SQL实现更新方法接口
	@Transactional
	@Query(value = "update t_wx_user set avatarurl=?1,nickname= ?2,telnum= ?3,gender= ?4,location= ?5,birthday= ?6,province= ?7,city= ?8,country=?9 where third_session=?10 ", nativeQuery = true)  
	@Modifying  
	public void updateBy3rd(String avatarurl,String nickname ,String telnum ,String gender ,String location ,String birthday ,String province ,String city,String country,String thirdSession); 



}
