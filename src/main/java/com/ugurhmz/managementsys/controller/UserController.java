package com.ugurhmz.managementsys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ugurhmz.managementsys.entity.User;
import com.ugurhmz.managementsys.services.UserService;



@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping("")
	public String getIndex() {
		
		return "index";
	}
	
	
	@GetMapping("/users")
	public String getAllUsers(Model model) {
		List<User> listUsers = userService.listAllUsers();
		
		model.addAttribute("listUsers",listUsers);
		return "users";
	}
	
}
