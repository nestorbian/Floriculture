package com.nestor.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "admin")
@Data
public class Admin {

    @Id
    private String adminId;
    private String adminName;
    private String password;
    private String phone;
    private String profile;
    private String headSculpturePath;
    private String headSculptureUrl;
    private LocalDateTime createTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;
    private LocalDateTime previousLoginTime;
    private LocalDateTime currentLoginTime;

}
