package com.App.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.App.Repository.AdminRepository;
import com.App.dto.Admin;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	public Admin SaveAdmin(Admin admin) {
		return adminRepository.save(admin);
	}
	public boolean emailExist(String email) {
		return adminRepository.existsByEmail(email);
	}
	
	public void removeSessionMessage() {
		HttpSession session= ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest()
        .getSession();
		session.removeAttribute("msg");

	}
	
	public void removeSessionMessage1() {
		HttpSession session= ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest()
        .getSession();
		session.removeAttribute("emsg");

	}
	

}
