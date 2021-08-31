package com.ugurhmz.managementsys.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
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
	
	
	@Test
	public void testCreateRestRoles() {
		Role salesPersonRole = new Role("Sales Person","I'm Sales Person   I  Manage product price, customer, shipping, orders and sales report"); 
		
		Role editorRole = new Role("Editor","I'm Editor, I  manage categories"
				+ ", brands, products, articles and menus");
		
		Role shipperRole = new Role("Shipper", "I'm Shipper, I manage view products"
				+ ", view orders");
	
		Role assistantRole = new Role("Assistant", "I'm Assistant, I manage questions and reviews");
		
		Role managerRole = new Role("Manager", "I'm Manager,Hiring and staffing ,Training new employees.");
		
		roleRepository.save(salesPersonRole);
		roleRepository.save(editorRole);
		roleRepository.save(shipperRole);
		roleRepository.save(assistantRole);
		roleRepository.save(managerRole);
		//roleRepository.saveAll(List.of(salesPersonRole, editorRole, shipperRole, assistantRole,managerRole));
		
		
	}
	
}
