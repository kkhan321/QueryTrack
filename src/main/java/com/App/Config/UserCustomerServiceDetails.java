package com.App.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.App.Repository.AdminRepository;
import com.App.Repository.EmployeeRepository;
import com.App.Repository.MyUserRepository;
import com.App.dto.Admin;
import com.App.dto.Employee;
import com.App.dto.MyUser;

@Component
public class UserCustomerServiceDetails implements UserDetailsService {
	@Autowired
	private MyUserRepository myUserRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		MyUser myUser = myUserRepository.findByEmail(email);
		if (myUser == null) {
			
			Admin admin = adminRepository.findByEmail(email);
			
			if (admin == null) {

				Employee employee=employeeRepository.findByEmail(email);

				if(employee==null) {

					throw new UsernameNotFoundException("User not found");
				}
				else {
					return new EmployeeCustom(employee);
				}

			} else {
				return new AdminCustom(admin);
			}
		} else {
			return new UserCustom(myUser);
		}
	}


}
