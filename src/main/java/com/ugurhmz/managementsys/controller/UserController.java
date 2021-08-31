package com.ugurhmz.managementsys.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
	
	
		
	
	
	
	
	
	
	// GET NEW ROLE
	@GetMapping("/users/add-role")
	public String getRolePage(Model model) {
		
		Role role = new Role();
		
		model.addAttribute("role",role);
		model.addAttribute("pageTitle","Create New Role");
		
		
		
		return "newRoleForm";
	}
	
	
	
	
	// POST ROLE
	@PostMapping("/users/save-role") // it'll be same th:action="@{/users/save-role}"
	public String saveRole(Role role, RedirectAttributes redirectAttributes) {
		
		userService.saveRole(role);
		
		redirectAttributes.addFlashAttribute("message","Role has been saved  successfully");
		redirectAttributes.addFlashAttribute("alertClass","alert-success");
		
		return "redirect:/users/add-role";
	}
	
	
	
	
	
	
	
	
	/*ALL USERS -> BEFORE PAGINATION 
	// GET ALL USERS
	@GetMapping("/users")
	public String getAllUsers(Model model) {
		List<User> listUsers = userService.listAllUsers();
		
		model.addAttribute("listUsers",listUsers);
		return "users";
	}
	*/
	
	
	
	
	// GET ALL USERS , AFTER SORTING & PAGINATION
	@GetMapping("/users")
	public String getFirstPageByPaginate(Model model) {
		return  sortAndPagination(1, model, "firstName","asc",null);
	}
	
	
	
	
	
	
	// GET NEW USER FORM
	@GetMapping("/users/new-user")
	public String getNewUser(Model model) {
		List<Role> listRoles = userService.listAllRoles();
		User user = new User();		  //We creating User
			 user.setEnabled(true);	  // user init enabled -> true	
		
		
		
		model.addAttribute("user",user);
		model.addAttribute("pageTitle","Create New User");
		model.addAttribute("roles",listRoles);
		
		
		return "newUserForm";
	}
	
	
	
	
	// POST NEW USER
		@PostMapping("/users/save")		//It'll be same in action  th:action="@{/users/save}
		public String postNewUser(
				User user, RedirectAttributes redirectAttributes,
				@RequestParam("imageInput") MultipartFile multipartFile ) throws IOException 
		{
			
			
			
			// with photos save
			if(!multipartFile.isEmpty()) {
				
				String fileName =  StringUtils.cleanPath(multipartFile.getOriginalFilename());
				user.setPhotos(fileName);
				
				User savedUser = userService.save(user);
				
				
				
				
				String uploadDirectory = "user-profile-photos/" + savedUser.getId();
				
				FileUploadUtil.cleanDirectory(uploadDirectory);				// Clean old photos
				FileUploadUtil.saveFile(uploadDirectory, fileName, multipartFile);
				
				
			} else {
				
				if(user.getPhotos().isEmpty())  user.setPhotos(null);
				userService.save(user);
			}
			
			//Message after redirect
			redirectAttributes.addFlashAttribute("message", "User has been saved  successfully.");
			redirectAttributes.addFlashAttribute("alertClass","alert-success");
			
			
			//redirect the registered user in the table
			String firstPartOfEmail = user.getEmail().split("@")[0];		//@  gördüğün yerden ayır, onun 0. indeksini al. Yani @gmail.com onda önceki olan ismi alıyor, onuda searchKeyword olarak veriyorki direk sanki arayıp onu bulmuş gibi redirect yapıyoruz 
			
			System.out.println(user.getEmail().split("@"));
			System.out.println("firstPartOfEmail : "+firstPartOfEmail);
			
			return  "redirect:/users/page/1?sortField=id&sortDir=asc&searchKeyword=" + firstPartOfEmail;
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
			redirectAttributes.addFlashAttribute("alertClass","alert-danger");
		} catch(UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message",e.getMessage());
		}
		
		return "redirect:/users/page/1?sortField=id&sortDir=asc";
	}
	
	
	
	
		
	// GET UPDATE USER
	@GetMapping("/users/update/{id}")					//coming from users.html ->  <a  class="btn btn-outline-warning fas fa-edit ml-1"th:href="@{'/users/update/' + ${user.id}}"title="Edit This User">
	public String updateUser(
			@PathVariable("id") Integer id ,
			Model model,
			RedirectAttributes redirectAttribute
			)
	{
		
		// if user has id 
		try {
			User user = userService.getUserById(id);	//Take it user with id
			
			List<Role> listAllroles = userService.listAllRoles();
			
			model.addAttribute("user",user);			//Send that user to the form
			model.addAttribute("pageTitle","User Update : "+id);
			model.addAttribute("roles",listAllroles);	// Send to all roles because user can change.
			
			return "newUserForm";
			
			
		} catch(UserNotFoundException ex) {		//if user has not id, Throw Exception
			redirectAttribute.addFlashAttribute("message",ex.getMessage());
			return "redirect:/users";
		}
	
	}
		

	
	
	//STATUS ENABLE / DISABLE
	@GetMapping("/users/{id}/enable/{status}")
	public String userStatus(
			@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttribute
			)
	{
		userService.userStatus(id, enabled);
		
		String statusInfo = enabled ? "enabled" : "disabled";
		String statusMessage = "The USER ID : "+id + " -> "+statusInfo;
		
		redirectAttribute.addFlashAttribute("alertClass","alert-info");
		redirectAttribute.addFlashAttribute("message",statusMessage);
		
		return "redirect:/users";
	}
	
	
	
    /*	// PAGINATION BEFORE SORTING
	@GetMapping("/users/page/{pageNumber}")
	public String listByPagination(
			@PathVariable("pageNumber") int pageNumber,
			Model model) 
	{
		Page<User> page = userService.listByPagination(pageNumber);
		List<User> listUsers = page.getContent();
		
		long startCount = (pageNumber - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE - 1 ;
		
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage",pageNumber);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalElements", page.getTotalElements());
		model.addAttribute("listUsers", listUsers);
		
		
		
		
		return "users";
	}*/
	
	
	
	// AFTER SORTING -> PAGINATION
	@GetMapping("/users/page/{pageNumber}")
	public String sortAndPagination(
			@PathVariable("pageNumber") int pageNumber,
			Model model,
			@Param("sortField") String sortField,
			@Param("sortDir") String sortDir,
			@Param("searchKeyword") String searchKeyword
			
			
			) 
	{
		
		System.out.println("sortField -> "+ sortField);
		System.out.println("sortDir -> "+sortDir);
		
		
		
		Page<User> page = userService.listByPagination(pageNumber, sortField, sortDir, searchKeyword);
		List<User> listUsers = page.getContent();
		
		
		long startCount = (pageNumber - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount  + UserService.USERS_PER_PAGE -1 ;
		
		
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("currentPage",pageNumber);
		model.addAttribute("startCount",startCount);
		model.addAttribute("endCount",endCount);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalElements",page.getTotalElements());
		model.addAttribute("listUsers",listUsers);
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDir);
		model.addAttribute("reverseSortDir",reverseSortDir);
		model.addAttribute("searchKeyword",searchKeyword);
		
		return "users";
	}
	
	
	
	
	
	
	
	
}




















