package com.ugurhmz.managementsys.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ugurhmz.managementsys.entity.User;



@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
