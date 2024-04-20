package com.App.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.App.Repository.AdminRepository;
import com.App.Service.EmployeeService;
import com.App.Service.QueryService;
import com.App.Service.UserService;
import com.App.dto.Admin;
import com.App.dto.Employee;
import com.App.dto.MyUser;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private UserService userService;

	@Autowired
	private QueryService queryService;



	@GetMapping("/EmpRegister")
	public String RegisterEmpGet(Model model) {
		model.addAttribute("employee", new Employee());
		return "empRegisteration";
	}

	@PostMapping("/EmpRegister")
	public String UserRegisterPost(@Valid Employee emp, BindingResult bindingResult,HttpSession session) {
		boolean b=employeeService.ExistsEmail(emp.getEmail());
		if(b) {
			session.setAttribute("emsg", "email already exists");
			return "redirect:/EmpRegister";
		}
		else if(bindingResult.hasErrors()) {
			return "empRegisteration";
		}
		else {
			emp.setPassword(encoder.encode(emp.getPassword()));
			Employee savedEmployee = employeeService.EmployeeSave(emp);

			System.out.println("ssssssucesssssssssssss");
			session.setAttribute("msg", "Register succesfullly");
			return "redirect:/admin/EmpRegister";			

		}

	}

	@GetMapping("/profile")
	public String profile() {
		return "adminProfile";
	}

	@ModelAttribute
	public void commUser1(Principal p,Model m) {
		if(p!=null) {
			String email=p.getName();
			Admin myAdmin=adminRepository.findByEmail(email);
			m.addAttribute("myAdmin", myAdmin);
		}
	}

	@GetMapping("/userlist")
	public String getUserlist(Model model) {
		model.addAttribute("users", userService.getUserlist());
		return "userlist";		
	}

	@GetMapping("/emplist")
	public String getEmplist(Model model) {
		model.addAttribute("employee", employeeService.getEmpList());
		return "emplist";		
	}

	@GetMapping("/queryList")
	public String getViewlist(Model model) {
		model.addAttribute("viewList",queryService.getQueryList());
		return "viewList";		
	}


}
