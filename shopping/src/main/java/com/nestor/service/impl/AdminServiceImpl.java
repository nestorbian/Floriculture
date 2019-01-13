package com.nestor.service.impl;

import java.time.LocalDateTime;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.nestor.common.BizException;
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
		
		try {
			adminRepository.save(admin);
		} catch (DataIntegrityViolationException e) {
			throw new ParameterException("该用户名已存在");
		}
		
		return adminId;
	}

}
