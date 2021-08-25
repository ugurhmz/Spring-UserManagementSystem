package com.ugurhmz.managementsys.repositories;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ugurhmz.managementsys.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

	
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Test
	public void testCreateFirstRole() {
		Role adminRole = new Role("Admin","I'm Admin, I can manage everything...");
		Role savedAdminRole = roleRepository.save(adminRole);
		
		assertThat(savedAdminRole.getId()).isGreaterThan(0);
	}
	
}
