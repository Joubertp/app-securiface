package com.backAppSecuriface.api.service;

import java.util.Collection;
import java.util.Optional;

import com.backAppSecuriface.api.exception.BusinessResourceException;
import com.backAppSecuriface.api.model.User;

public interface UserService {

	Collection<User> getAllUsers();
	
	Optional<User> getUserById(Long id) throws BusinessResourceException;
	
	Optional<User> findByEmail(String email) throws BusinessResourceException;
	
	User saveOrUpdateUser(User user) throws BusinessResourceException;
	
	void deleteUser(Long id) throws BusinessResourceException;

	Optional<User> findByEmailAndPassword(String email, String password) throws BusinessResourceException;

}
