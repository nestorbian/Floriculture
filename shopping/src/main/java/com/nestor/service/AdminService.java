package com.nestor.service;

import com.nestor.entity.Admin;

public interface AdminService {
	
	public Admin findByAdminNameAndPassword(String adminName, String password);
	public String add(Admin admin);
	
}
