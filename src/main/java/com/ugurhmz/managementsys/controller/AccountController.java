package com.ugurhmz.managementsys.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ugurhmz.managementsys.FileUploadUtil;
import com.ugurhmz.managementsys.entity.User;
import com.ugurhmz.managementsys.security.SecurityUserDetails;
import com.ugurhmz.managementsys.services.UserService;




@Controller
public class AccountController {

	@Autowired
	private UserService userService;
	
	
	// GET ACCOUNT PAGE
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
	
	
	
	//POST ACCOUNT 
	@PostMapping("/account/update")
	public String postAccountDetails(
			User user,
			@AuthenticationPrincipal SecurityUserDetails loggedUser,
			RedirectAttributes redirectAttributes,
			@RequestParam("imageInput") MultipartFile multipartFile) throws IOException 
	{
		
		if(!multipartFile.isEmpty()) {
			
			String fileName =  StringUtils.cleanPath(multipartFile.getOriginalFilename());
					user.setPhotos(fileName);
			
			User savedUser = userService.accountUpdate(user);
			
			String uploadDirectory = "user-profile-photos/" +savedUser.getId();
			FileUploadUtil.cleanDirectory(uploadDirectory);	//Clean old photos
			FileUploadUtil.saveFile(uploadDirectory, fileName, multipartFile);
			
		} else {
			
			if(user.getPhotos().isEmpty())  user.setPhotos(null);
			userService.accountUpdate(user);
		}
		
		
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		
		
		redirectAttributes.addFlashAttribute("message","Account has been updated");
		
		
		return "redirect:/account";
	}
	
}













