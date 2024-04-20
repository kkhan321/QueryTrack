package com.App.Config;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Set<String> roles=AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		System.out.println(roles);
		if(roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin/profile");
		}
		else if(roles.contains("ROLE_EMP")) {
			response.sendRedirect("/emp/Profile");
		}
		else {
			response.sendRedirect("/user/profile");
		}
		
	}

}
