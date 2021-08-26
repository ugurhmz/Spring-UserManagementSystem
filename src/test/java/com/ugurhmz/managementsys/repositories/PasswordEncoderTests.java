package com.ugurhmz.managementsys.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTests {

	
		
	  @Test
	  public void encodePasswordTest() {
		  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		  
		  String myPassword = "selamlar123";
		  String encodedPassword = passwordEncoder.encode(myPassword);
		  
		  System.out.println("Encoded password : "+encodedPassword);
		  
		  
		  boolean passwordMatches = passwordEncoder.matches(myPassword, encodedPassword);
		  
		  assertThat(passwordMatches).isTrue();
	  }
	
}
