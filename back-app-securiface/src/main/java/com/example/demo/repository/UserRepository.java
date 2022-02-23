package com.example.demo.repository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.metier.User;

public interface UserRepository extends  PagingAndSortingRepository<User, Integer> {

	Optional<User> findByUsername( String username);
	
	// Utilise pour MyUserDetailsService. 
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username=:username")
	Optional<User> findByUsernameWithRole(@Param("username") String username);
	
	@Query("SELECT u FROM User u")
	<T>Page<T> findAll(Class<T> type, Pageable page);
}
