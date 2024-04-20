package com.App.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.App.Repository.MyUserRepository;
import com.App.dto.MyUser;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
	
	@Autowired
	private MyUserRepository myUserRepository;
	
	public boolean ExistEmail(String email) {
		return myUserRepository.existsByEmail(email);
	}
	public MyUser SaveUser(MyUser myUser) {
		return myUserRepository.save(myUser);
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
	
	public List<MyUser> getUserlist(){
		return myUserRepository.findAll();
	}
	
	

}
