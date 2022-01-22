package com.backAppSecuriface.api.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backAppSecuriface.api.model.User;
import com.backAppSecuriface.api.service.UserService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/user/*")
public class UserController {

	@Autowired
	private UserService userService;	
	
	@GetMapping(value = "/users")
	public ResponseEntity<Collection<User>> getAllUsers() {
		Collection<User> users = userService.getAllUsers();
		return new ResponseEntity<Collection<User>>(users, HttpStatus.FOUND);
	}
}

