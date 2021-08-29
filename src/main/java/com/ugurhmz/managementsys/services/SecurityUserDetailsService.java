package com.ugurhmz.managementsys.services;

import org.springframework.beans.factory.annotation.Autowired;


import com.ugurhmz.managementsys.entity.User;
import com.ugurhmz.managementsys.repositories.UserRepository;
import com.ugurhmz.managementsys.security.SecurityUserDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.getByUserEmail(email);
		
		if(user != null) {
			return new SecurityUserDetails(user);
		}
		
		throw new UsernameNotFoundException("Could not find user with email : "+email);
		
	}

}