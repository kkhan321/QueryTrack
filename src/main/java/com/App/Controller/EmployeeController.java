package com.App.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.App.Repository.EmployeeRepository;
import com.App.Service.EmployeeService;
import com.App.Service.QueryService;
import com.App.Service.UserService;
import com.App.dto.Employee;
import com.App.dto.Query;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/emp")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private QueryService queryService;

	@GetMapping("/Profile")
	public String profile() {
		return "empProfile";
	}

	@ModelAttribute
	public void commUser2(Principal p,Model m) {
		if(p!=null) {
			String email=p.getName();
			Employee emp=employeeRepository.findByEmail(email);
			m.addAttribute("emp", emp);
		}

	}

	@GetMapping("/userlist")
	public String getUserlist(Model model) {
		model.addAttribute("users", userService.getUserlist());
		return "userlist";
	}

	@GetMapping("/queryList/{emp_id}")
	public String getQuerylist(@PathVariable (value = "emp_id") int emp_id,Model model) {
		model.addAttribute("viewList", queryService.getQueryListByEmp(emp_id));
		return "empQueryList";
	}

	@GetMapping("/readUser/{id}")
	public String readUserQuery(@PathVariable(value = "id") int id,HttpSession session) {		
		Query query=queryService.getQueryById(id);
		String updateStatus="read";
		query.setStatus(updateStatus);
		Query updatedQuery = queryService.saveQuery(query);
		int emp_id=updatedQuery.getEmp().getId();
		if(updatedQuery.getStatus()=="read") {
			System.out.println(query.getStatus());
			session.setAttribute("msg", "Your Query is Readed");
		}
		else {
			session.setAttribute("emsg", "something went wrong");

		}

		return "redirect:/emp/queryList/"+emp_id; 


	}

	@GetMapping("/dumpUser/{id}")
	public String dumpUserQuery(@PathVariable(value = "id") int id,HttpSession session) {		
		Query query=queryService.getQueryById(id);
		String updateStatus="dump";
		query.setStatus(updateStatus);
		Query updatedQuery= queryService.saveQuery(query);
		int emp_id=updatedQuery.getEmp().getId();
		if(query.getStatus()=="dump") {
			session.setAttribute("msg", "Your Query is dumbed");
		}
		else {
			session.setAttribute("emsg", "something went wrong");

		}
		//int empId = Integer.parseInt(request.getParameter("emp_id"));
		return "redirect:/emp/queryList/"+emp_id; 


	}


}
