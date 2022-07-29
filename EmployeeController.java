package com.greatlearning.controller;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.greatlearning.entity.Employee;
import com.greatlearning.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	EmployeeService employeeServiceImpl;

	@RequestMapping("/list")
	public String listEmployee(Model model) {

		List<Employee> employee = employeeServiceImpl.findAll();

		model.addAttribute("Employee", employee);

		return "list-employee";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {

		Employee employee = new Employee();

		model.addAttribute("Employee", employee);

		return "Employee-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("EmployeeId") int id, Model model) {

		Employee employee = employeeServiceImpl.findById(id);

		model.addAttribute("Employee", employee);

		return "Employee-form";
	}

	@RequestMapping("/save")
	public String save(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("course") String course,
			@RequestParam("country") String country) {

		Employee employee;

		if (id != 0) {
			employee = employeeServiceImpl.findById(id);
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employee.setCourse(course);
			employee.setCountry(country);

		} else {

			employee = new Employee(firstName, lastName, course, country);

		}

		employeeServiceImpl.save(employee);

		return "redirect:/employee/list";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("employeeId") int id) {
		
		employeeServiceImpl.deleteById(id);
		return "redirect:/employee/list"; 
	}
	
	@RequestMapping(value = "/403")
	public ModelAndView accessDenied(Principal user) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		if(user!=null) {
			modelAndView.addObject("msg", "Hey  "+ user.getName()+"  You dont have permission to access this page");
		}else {
			modelAndView.addObject("msg", "You dont have permission to access this page");
		}
		modelAndView.setViewName("403");
		return modelAndView;
		
	}

}

