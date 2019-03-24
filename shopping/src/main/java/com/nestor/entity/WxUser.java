package com.nestor.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;


/**
 * The persistent class for the t_wx_user database table.
 * 
 */
@Entity
@Table(name="t_wx_user")
@DynamicUpdate
@Data
public class WxUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String openid;

	private String avatarurl;

	private String city;

	private String country;

	private int ctime;

	private String gender;

	private String language;

	private String mobile;

	private String nickname;

	private String province;
	
	private String birthday;
	
	private String location;

	@Column(name="session_key")
	private String session_key;

	private String telnum;

	@Column(name="third_session")
	private String thirdSession;

	private String unionid;

	public WxUser() {
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getAvatarurl() {
		return this.avatarurl;
	}

	public void setAvatarurl(String avatarurl) {
		this.avatarurl = avatarurl;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getCtime() {
		return this.ctime;
	}

	public void setCtime(int ctime) {
		this.ctime = ctime;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String session_key() {
		return this.session_key;
	}

	public void setSessionKey(String session_key) {
		this.session_key = session_key;
	}

	public String getTelnum() {
		return this.telnum;
	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	public String getThirdSession() {
		return this.thirdSession;
	}

	public void setThirdSession(String thirdSession) {
		this.thirdSession = thirdSession;
	}

	public String getUnionid() {
		return this.unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

}