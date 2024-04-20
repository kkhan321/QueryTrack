package com.App.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.App.Repository.EmployeeRepository;
import com.App.dto.Employee;
import com.App.dto.Query;

import jakarta.servlet.http.HttpSession;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	public Employee EmployeeSave(Employee emp) {
		return employeeRepository.save(emp);
	}
	
	public boolean ExistsEmail(String email) {
		return employeeRepository.existsByEmail(email);
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
	public List<Employee> getEmpList(){
		return employeeRepository.findAll();
	}
	
	

}
