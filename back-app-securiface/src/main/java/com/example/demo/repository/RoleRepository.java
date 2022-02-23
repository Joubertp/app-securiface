package com.example.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.metier.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {

	Role findByRoleName(String roleName);
	
}