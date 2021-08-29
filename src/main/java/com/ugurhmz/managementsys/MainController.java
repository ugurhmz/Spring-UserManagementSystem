package com.ugurhmz.managementsys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ugurhmz.managementsys.entity.User;
import com.ugurhmz.managementsys.services.UserService;

@Controller
public class MainController {

	@Autowired
	private UserService userService;
	
	
	
	// GET INDEX PAGE
	@GetMapping("")
	public String getIndex(User user, Model model) {
		
		
		model.addAttribute("roleSize",userService.listAllRoles().size());
		model.addAttribute("allRoles",userService.listAllRoles());
		model.addAttribute("userSize",userService.listAllUsers().size());
		model.addAttribute("allUsers",userService.listAllUsers());
		
		return "index";
	}
	

	// login
	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}
	
	
		
	
}
