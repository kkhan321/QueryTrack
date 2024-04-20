package com.App.Config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.App.dto.MyUser;


public class UserCustom implements UserDetails{
	
	private MyUser myUser;
	
	public UserCustom(MyUser myUser) {
		super();
		this.myUser = myUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority=new SimpleGrantedAuthority(myUser.getRole());
		return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		
		return myUser.getPassword();
	}

	@Override
	public String getUsername() {	
		return myUser.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
