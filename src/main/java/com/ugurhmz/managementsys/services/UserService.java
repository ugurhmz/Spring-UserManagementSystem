package com.ugurhmz.managementsys.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ugurhmz.managementsys.entity.Role;
import com.ugurhmz.managementsys.entity.User;
import com.ugurhmz.managementsys.repositories.RoleRepository;
import com.ugurhmz.managementsys.repositories.UserRepository;



@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	// LIST ALL USERS
	public List<User> listAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	
	// CREATE & UPDATE
	public User save(User user) {
		boolean isUpdateUser = (user.getId() != null);
		
			// it means it has user id update process -> User id'ye sahipse demekki güncelleme işlemi
			if(isUpdateUser) {
				User existingUser = userRepository.findById(user.getId()).get();
				
				
					// User passwordu boşsa, demekki password değişmemiş, o halde var olan passwordu kullan.
					if(user.getPassword().isEmpty()) {
						user.setPassword(existingUser.getPassword());
					}
					
					//password değişmişse güncelleme yaparken, onuda encode et.
					else {
						encodePassword(user);
					}
				
			}
				
			// it has not user id, NEW USER process  -> User  id'si null ise yeni kullanıcı önce pw encode,sonra db kay
			else {
				encodePassword(user);
			}
			
			
			
			return userRepository.save(user);
	}
	
	
	// password Encode
	public void encodePassword(User user) {
		String encodedPassword =   passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	
	
	// LIST ALL ROLES 
	public List<Role> listAllRoles() {
		return (List<Role>) roleRepository.findAll();
	}
	
	
	
	//Email is Unique ?
		public boolean isEmailUnique(Integer id, String email) {
			User userEmail = userRepository.getByUserEmail(email);
			
			if(userEmail == null) return true;
			
			boolean isCreatingNew = (id == null);
			
			if(isCreatingNew) {
				if(userEmail != null) return false;
				
			} else {
				if(userEmail.getId() != id) {
					return false;
				}
			}
			
			return true;
		}
	
	
	// DELETE USER
	public void delete(Integer id) throws UserNotFoundException {
		Long takeById = userRepository.countById(id);
		
		if(takeById == null || takeById == 0) {		// if user id null -> Throw Exception
			throw new UserNotFoundException("Could not find any user ID : "+id);
		}
		
		// user has been id then delete it.
		userRepository.deleteById(id);
	}


	
	
	// UPDATE USER  - GET USER BY ID
	public User getUserById(Integer id) throws UserNotFoundException {
		//if user has  id 
		try {
			return userRepository.findById(id).get();
			
		} catch(NoSuchElementException ex) {		// handle exception
			throw new UserNotFoundException("Could not find any user with ID : "+id); 		//throw our custom exception
		}
	}
	
	
	
}












