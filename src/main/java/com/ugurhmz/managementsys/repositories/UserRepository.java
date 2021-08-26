package com.ugurhmz.managementsys.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ugurhmz.managementsys.entity.User;



@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	
	// FOR isEmailUnique
	@Query("SELECT u FROM  User u WHERE u.email = :email")
	public User getByUserEmail(@Param("email") String email);
	
	
}
