package com.nestor.entity;


import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;


/**
 * The persistent class for the t_wx_user database table.
 * 
 */
@Entity
@Table(name="t_wx_address")
@DynamicUpdate
@Data
public class WxAddress {
	
	
	//地址信息编号
    @Id
	private String id;

    private String openid;
	
	private String username;

	private String telnum;

	private String location;

	private String country;

	private String province;

	private String city;

	private String detailAdd;

}
