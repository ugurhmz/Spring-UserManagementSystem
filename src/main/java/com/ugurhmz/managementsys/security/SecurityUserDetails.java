package com.ugurhmz.managementsys.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ugurhmz.managementsys.entity.Role;
import com.ugurhmz.managementsys.entity.User;



public class SecurityUserDetails implements UserDetails {

	private User user;
	
	
	
	public SecurityUserDetails(User user) {
		this.user = user;
	}
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		Set<Role> roles = user.getRoles();
		List<SimpleGrantedAuthority> authories = new ArrayList<>();
		
		for(Role role : roles) {
			authories.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return authories;
	}

	
	// password
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	
	
	// email
	@Override
	public String getUsername() {
		return user.getEmail();
	}

	
	// is Account Non expired?
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	
	
	// is Account Non Locked ?
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	
	
	// is Credentials Non Expired ?
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	
	
	// is Enabled ? 
	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

	

}