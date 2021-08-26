package com.ugurhmz.managementsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ugurhmz.managementsys.entity.User;
import com.ugurhmz.managementsys.repositories.RoleRepository;
import com.ugurhmz.managementsys.repositories.UserRepository;



@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	
	// LIST ALL USERS
	public List<User> listAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	
	
}
