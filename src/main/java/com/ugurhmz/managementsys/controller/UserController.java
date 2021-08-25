package com.ugurhmz.managementsys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class UserController {

	
	
	@GetMapping("")
	public String getIndex() {
		
		return "index";
	}
}
