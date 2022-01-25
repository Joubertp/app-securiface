package com.backAppSecuriface.api.controller;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backAppSecuriface.api.model.User;
import com.backAppSecuriface.api.service.UserService;

@CrossOrigin(origins = "http://127.0.0.1:9000", maxAge = 3600)
@RestController
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserService userService;	

	@GetMapping(value = "/users")
	public ResponseEntity<Collection<User>> getAllUsers() {
		Collection<User> users = userService.getAllUsers();
		return new ResponseEntity<Collection<User>(users, HttpStatus.FOUND);
	}
	
	@PostMapping(value = "/users")
	@Transactional
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		
		User userSaved = userService.saveOrUpdateUser(user);		
 		return new ResponseEntity<User>(userSaved, HttpStatus.CREATED);
 	}
	@PostMapping(value = "/users/login")
	public ResponseEntity<User> findUserByEmailAndPassword(@RequestBody User user) {
		
		Optional<User> userFound = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
		return new ResponseEntity<User>(userFound.get(), HttpStatus.FOUND);
	}
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<User> findUserById(@PathVariable(value = "id") Long id) {
		Optional<User> userFound = userService.getUserById(id);
		return new ResponseEntity<User>(userFound.get(), HttpStatus.FOUND);
	}

}

