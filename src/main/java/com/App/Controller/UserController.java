package com.App.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.App.Repository.MyUserRepository;
import com.App.Service.EmployeeService;
import com.App.Service.QueryService;
import com.App.Service.UserService;
import com.App.dto.MyUser;
import com.App.dto.Query;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private UserService userService;

	@Autowired
	private MyUserRepository myUserRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private QueryService queryService;

	@GetMapping("/profile")
	public String profile(Model model,Model model2) {
		model.addAttribute("query", new Query());
		model2.addAttribute("emplist", employeeService.getEmpList());
		return "userProfile";
	}

	@GetMapping("/changepassword")
	public String changepassword() {
		return "changePassword";
	}

	@PostMapping("/changePassword")
	public String changePassword(Principal p, @RequestParam("oldPass") String oldPass, 
			@RequestParam("newPass") String newPass,HttpSession session) {

		String email = p.getName();
		MyUser myUser = myUserRepository.findByEmail(email);

		boolean f = encoder.matches(oldPass, myUser.getPassword());

		if(f) {
			myUser.setPassword(encoder.encode(newPass));
			MyUser updatePassUser=userService.SaveUser(myUser);
			if(updatePassUser!=null) {
				session.setAttribute("msg", "password is changed succesfully");
			}
			else {
				session.setAttribute("emsg", "somethong went wrong");

			}
		}
		else {

			session.setAttribute("emsg", "your old password is not match");

			System.out.println("Wrong Old Password");
		}

		return "redirect:/user/changepassword";
	}


	@GetMapping("/editUser")
	public String editUserPage() {
		return "editprofile";
	}

	@PostMapping("/updateUser")
	public String editUser(Principal p, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("contact") String phone,@RequestParam("gender") String gender, HttpSession session){

		String pEmail = p.getName();
		MyUser myUser = myUserRepository.findByEmail(pEmail);

		myUser.setName(name);
		myUser.setEmail(email);
		myUser.setContact(phone);
		myUser.setGender(gender);

		MyUser updatedUser = userService.SaveUser(myUser);

		if (updatedUser != null) {
			session.setAttribute("msg", "Profile Details Updated Successfully...!");
			return "editProfile";
			//System.out.println("--->"+updatedUser);
		}
		else {
			session.setAttribute("emsg", "Something went wrong");
			System.out.println("--->"+"not successss");

		}
		return "redirect:/user/editUser";

	}

	@ModelAttribute
	public void commUser(Principal p,Model m) {
		if(p!=null) {
			String email=p.getName();
			MyUser user=myUserRepository.findByEmail(email);
			m.addAttribute("user", user);
		}



	}

	@PostMapping("/addQuery") 
	public String AddQuery(@Valid Query query, BindingResult bindingResult,HttpSession session) {
		if(bindingResult.hasErrors()) {
			System.out.println("-------------------------gggg");
			return "userProfile";
		}
		else {
			query.setCreatedAt(LocalDateTime.now());
			Query Savequery= queryService.saveQuery(query);
			session.setAttribute("msg", "your query is submitted succesfully");
			return "redirect:/user/addQuery";

		}
	}

	@GetMapping("/viewquery/{my_user_id}")
	public String viewQuery(@PathVariable(value = "my_user_id") int my_user_id, Model model) {
		model.addAttribute("viewList",queryService.getQuerlistByUser(my_user_id));
		return "Querylist";
	}

	@GetMapping("/addQuery")
	public String addQuery(Model model,Model model2) {
		model.addAttribute("query", new Query());
		model2.addAttribute("emplist", employeeService.getEmpList());
		return "addQuery";
	}

}
