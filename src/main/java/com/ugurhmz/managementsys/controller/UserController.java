package com.ugurhmz.managementsys.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ugurhmz.managementsys.FileUploadUtil;
import com.ugurhmz.managementsys.entity.Role;
import com.ugurhmz.managementsys.entity.User;
import com.ugurhmz.managementsys.services.UserNotFoundException;
import com.ugurhmz.managementsys.services.UserService;





@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	// GET INDEX PAGE
	@GetMapping("")
	public String getIndex() {
		
		return "index";
	}
	
	
	
	// GET ALL USERS
	@GetMapping("/users")
	public String getAllUsers(Model model) {
		List<User> listUsers = userService.listAllUsers();
		
		model.addAttribute("listUsers",listUsers);
		return "users";
	}
	
	
	
	
	// GET NEW USER FORM
	@GetMapping("/users/new-user")
	public String getNewUser(Model model) {
		
		User user = new User();		  //We creating User
			 user.setEnabled(true);	  // user init enabled -> true	
		
		List<Role> listRoles = userService.listAllRoles();
		
		
		model.addAttribute("pageTitle","Create New User");
		model.addAttribute("user",user);
		model.addAttribute("roles",listRoles);
		
		
		return "newUserForm";
	}
	
	
	// POST NEW USER
	@PostMapping("/users/save") 	// It'll be same in action  th:action="@{/users/save}
	public String postNewUser(
			User user, 
			RedirectAttributes redirectAttributes,
			Model model,
			@RequestParam("imageInput") MultipartFile multipartFile		// newUserForm  -> <input  type="file" accept="image/png, image/jpeg" name="imageInput" 	class="form-control-file w-50" id="fileImage" /> coming from here.
			) throws IOException 
	{
		
		// with photos save
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			
			User savedUser = userService.save(user);
			
			String uploadDirectory = "user-profile-photos/" + savedUser.getId();
			FileUploadUtil.cleanDirectory(uploadDirectory);				// Clean old photos
			FileUploadUtil.saveFile(uploadDirectory, fileName, multipartFile);
			
			
		} else {
			
			if(user.getPhotos().isEmpty()) {
				user.setPhotos(null);
			}
			
			userService.save(user);	
		}
		
		
		
		
		
		//without photos save
		userService.save(user);
		
		//Message after redirect
		redirectAttributes.addFlashAttribute("message", "User has been saved  successfully.");
		
		return "redirect:/users";
		
		
	}
	
	
	
	// DELETE USER
		@GetMapping("/users/delete/{id}")
		public String userDelete(
				@PathVariable("id") Integer id, 
				Model model, 
				RedirectAttributes redirectAttributes) 
		{
			try {
				userService.delete(id);
				redirectAttributes.addFlashAttribute("message","The USER ID : "+id+" has been deleted successfully.");
				
			} catch(UserNotFoundException e) {
				redirectAttributes.addFlashAttribute("message",e.getMessage());
			}
			
			return "redirect:/users";
		}
	
	
	
	
	
	
}
