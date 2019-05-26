package com.nestor.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

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
    @CreationTimestamp
    private LocalDateTime createTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;

}