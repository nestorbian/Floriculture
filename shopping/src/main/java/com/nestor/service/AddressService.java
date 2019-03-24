package com.nestor.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nestor.entity.WxAddress;

public interface AddressService extends JpaRepository<WxAddress, String> {
	//原生SQL实现更新方法接口
		@Transactional
		@Query(value = "select * from  t_wx_address  where openid=?1 ", nativeQuery = true)  
		@Modifying  
		public ArrayList<WxAddress> findAddressList(String openid); 
}
