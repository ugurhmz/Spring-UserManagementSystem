package com.ugurhmz.managementsys.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ugurhmz.managementsys.entity.Role;




@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

}