package com.backAppSecuriface.api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backAppSecuriface.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	// Collection<User> getAllUsers();
	Optional<User> findByEmail(String email);
}
