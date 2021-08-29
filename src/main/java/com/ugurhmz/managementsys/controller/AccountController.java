package com.ugurhmz.managementsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ugurhmz.managementsys.entity.User;
import com.ugurhmz.managementsys.security.SecurityUserDetails;
import com.ugurhmz.managementsys.services.UserService;




@Controller
public class AccountController {

	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/account")
	public String getAccountDetails(
			@AuthenticationPrincipal SecurityUserDetails loggedUser,
			Model model) 
	{
		
		String email = loggedUser.getUsername();
		User user =	userService.getByUserEmail(email);
		
				
		model.addAttribute("user",user);
		model.addAttribute("pageTitle","Account Detail");
		
		return "accountDetailForm";
		
	}
	
	
}













