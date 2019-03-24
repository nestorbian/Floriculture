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

	private String detail_add;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelnum() {
		return telnum;
	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDetail_add() {
		return detail_add;
	}

	public void setDetail_add(String detail_add) {
		this.detail_add = detail_add;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "WxAddress [id=" + id + ", openid=" + openid + ", username=" + username + ", telnum=" + telnum
				+ ", location=" + location + ", country=" + country + ", province=" + province + ", city=" + city
				+ ", detail_add=" + detail_add + "]";
	}


}
