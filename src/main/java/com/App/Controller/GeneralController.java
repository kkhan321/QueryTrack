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

import com.App.Repository.AdminRepository;
import com.App.Repository.EmployeeRepository;
import com.App.Repository.MyUserRepository;
import com.App.Service.AdminService;
import com.App.Service.UserService;
import com.App.dto.Admin;
import com.App.dto.Employee;
import com.App.dto.MyUser;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class GeneralController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService  userService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private MyUserRepository myUserRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/Uregister")
	public String UserRegister(Model model) {
		model.addAttribute("myUser", new MyUser());
		return "userRegisteration";
	}

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/Aregister")
	public String AdminRegister(Model model) {
		model.addAttribute("admin", new Admin());
		return "adminRegister";
	}

	@PostMapping("/adminRegister")
	public String AdminRegisterPost(@Valid Admin admin, BindingResult bindingResult,HttpSession session) {
		boolean b=adminService.emailExist(admin.getEmail());
		if(b) {
			session.setAttribute("emsg", "email already exists");
			return "redirect:/Aregister";
		}
		else if(bindingResult.hasErrors()) {
			return "adminRegister";
		}
		else{
			admin.setPassword(encoder.encode(admin.getPassword()));
			Admin savedAdmin = adminService.SaveAdmin(admin);

			System.out.println("ssssssucesssssssssssss");
			session.setAttribute("msg", "Register succesfullly");
			return "redirect:/Aregister";			
		}
	}

	@PostMapping("/registerUser")
	public String UserRegisterPost(@Valid MyUser myUser, BindingResult bindingResult,HttpSession session) {
		boolean b=userService.ExistEmail(myUser.getEmail());
		if(b) {
			session.setAttribute("emsg", "email already exists");
			return "redirect:/Uregister";
		}
		else if(bindingResult.hasErrors()) {
			return "userRegisteration";
		}
		else {
			myUser.setPassword(encoder.encode(myUser.getPassword()));
			MyUser savedUser = userService.SaveUser(myUser);

			System.out.println("ssssssucesssssssssssss");
			session.setAttribute("msg", "Register succesfullly");
			return "redirect:/Uregister";			

		}
	}

	@ModelAttribute
	public void commUser(Principal p,Model m) {
		if(p!=null) {
			String email=p.getName();
			MyUser Myuser=myUserRepository.findByEmail(email);
			m.addAttribute("user", Myuser);
		}

	}

	@ModelAttribute
	public void commUser1(Principal p,Model m) {
		if(p!=null) {
			String email=p.getName();
			Admin admin=adminRepository.findByEmail(email);
			m.addAttribute("myAdmin", admin);
		}

	}

	@ModelAttribute
	public void commUser2(Principal p,Model m) {
		if(p!=null) {
			String email=p.getName();
			Employee emp=employeeRepository.findByEmail(email);
			m.addAttribute("emp", emp);
		}

	}


}
