package com.nestor.service;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nestor.entity.WxUser;

//继承JpaRepository<entity类型  , 主键类型>
public interface WxLoginService extends JpaRepository<WxUser, String> {
	//public List findById(String openid);
	//public WxUser saveAndFlush(WxUser wxUser);
	
	//原生SQL实现更新方法接口
	@Transactional
	@Query(value = "update t_wx_user set session_key=?2,third_session=?3,unionid=?4 where openid=?1 ", nativeQuery = true)  
	@Modifying  
	public void updateOne(String openid,String session_key,String setThirdSession,String unionid); 

}
