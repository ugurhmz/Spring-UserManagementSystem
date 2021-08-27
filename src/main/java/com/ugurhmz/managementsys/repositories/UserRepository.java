package com.ugurhmz.managementsys.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ugurhmz.managementsys.entity.User;



@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	
	// countById KULLAN -> find yerine bu kullanılır, Kendin isim verirsen HATA ALIRSIN
	//If you give a custom name it, you will get an error.  don't USE Example names : takeById, heyMyId...
	public Long countById(Integer id);
	
	
	
	// FOR isEmailUnique
	@Query("SELECT u FROM  User u WHERE u.email = :email")
	public User getByUserEmail(@Param("email") String email);
	
	
	
	// Status enabled/disabled
	@Query("UPDATE User u SET  u.enabled = ?2 WHERE u.id=?1")
	@Modifying
	public void userStatusEnableDisable(Integer id, boolean enabled);
	
	
	/* NOT
	 * The @Modifying annotation is used to enhance the @Query annotation to execute not only SELECT
	 *  queries but also INSERT, UPDATE, DELETE, and even DDL queries.
	 * */
	
	
}
