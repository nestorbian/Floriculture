package com.nestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nestor.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
	
	public Admin findByAdminNameAndPassword(String adminName, String password);
	
}
