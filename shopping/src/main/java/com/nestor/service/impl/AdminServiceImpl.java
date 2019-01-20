package com.nestor.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.nestor.common.ParameterException;
import com.nestor.entity.Admin;
import com.nestor.repository.AdminRepository;
import com.nestor.service.AdminService;
import com.nestor.util.IdUtil;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Admin findByAdminNameAndPassword(String adminName, String password) {
		Admin admin = adminRepository.findByAdminNameAndPassword(adminName, password);
		
		if (admin != null) {
			// 更新上一次登录时间以及此次登录时间
			admin.setPreviousLoginTime(admin.getCurrentLoginTime());
			admin.setCurrentLoginTime(LocalDateTime.now());
			adminRepository.save(admin);
			
			admin.setPassword(null);
		}
		
		return admin;
	}

	@Override
	public String add(Admin admin) {
		String adminId = IdUtil.generateId();
		admin.setAdminId(IdUtil.generateId());
		
		Admin match = new Admin();
		match.setAdminName(admin.getAdminName());
		if (adminRepository.exists(Example.of(match))) {
			throw new ParameterException("该用户名已存在");
		}
		
		adminRepository.save(admin);
		
		return adminId;
	}

}
