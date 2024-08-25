package com.user.entity;


import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

	RoleEntity findByName(String name);
	
}
