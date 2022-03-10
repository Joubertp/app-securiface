package com.example.demo.service;


import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.var;

import org.springframework.stereotype.Service;

import com.example.demo.metier.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	  @Autowired
	  private UserRepository userRepository;
	  
	  @Autowired
	  private PasswordEncoder passwordEncoder;

	  public ResponseEntity<User> create(User user) {
		  	userRepository.findByEmail(user.getEmail()).ifPresent(userSelected -> {
			    throw new IllegalArgumentException("Un utilisateur existe déjà !");
			  });
		  	  PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		  	  user.setPassword(encoder.encode(user.getPassword()));
			  return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
	    }
	  
	  public ResponseEntity<?> findById(Long id) {
	        var val = userRepository.findById(id);
	        if (!val.isPresent()) {
	        	return new ResponseEntity<String>("Pas d'utilisateur", HttpStatus.BAD_REQUEST); 
	        }    
	        return new ResponseEntity<>(Optional.ofNullable(val.get()), HttpStatus.ACCEPTED);
	  }
	  
	  public ResponseEntity<?> findByEmailAndPassword(User user) {
	       	var userSelected = userRepository.findByEmail(user.getEmail());
	        if (userSelected.isPresent()) {
	        	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	        	String passwordEncoded = encoder.encode(user.getPassword());
	        	if (passwordEncoded == userSelected.get().getPassword()) {
	        		return new ResponseEntity<>(Optional.of(userSelected.get()), HttpStatus.ACCEPTED);
	        	}
	        } else {
	        	return new ResponseEntity<String>("Pas d'utilisateur", HttpStatus.BAD_REQUEST);
	        }
			return new ResponseEntity<>(Optional.ofNullable(userSelected.get()), HttpStatus.ACCEPTED);
	  }
	  
	  public ResponseEntity<?> update(User user) {
		  	Optional<User> exists = userRepository.findById(user.getId());
		  	if(exists.isEmpty()) {
		  		return new ResponseEntity<String>("Utilisateur inconnu", HttpStatus.BAD_REQUEST); 
		  	}
		  	//on ne peut pas modififer le psword avec cette requette
		  	user.setPassword(exists.get().getPassword());
		  	return new ResponseEntity<User>(userRepository.save(user), HttpStatus.ACCEPTED);
	  }
	  
	  public ResponseEntity<?> updatePswd(User user) {
		  	Optional<User> exists = userRepository.findById(user.getId());
		  	if(exists.isEmpty()) {
		  		return new ResponseEntity<String>("Utilisateur inconnu", HttpStatus.BAD_REQUEST); 
		  	}
		  	exists.get().setPassword(passwordEncoder.encode(user.getPassword()));
		  	return new ResponseEntity<User>(userRepository.save(exists.get()), HttpStatus.ACCEPTED);
	  }

	  public ResponseEntity<String> delete(Long id) {
			boolean exists = userRepository.existsById(id);
			if(!exists) {
				return new ResponseEntity<String>("Utilisateur inconnu", HttpStatus.BAD_REQUEST); 
			}
			userRepository.deleteById(id);
			return new ResponseEntity<String>("utilisateur supprimé", HttpStatus.ACCEPTED);
	  }
	  
	  public ResponseEntity<?> deleteAll() {
		  	var it = userRepository.findAll();
	        it.forEach(e -> this.delete(e.getId()));
			return new ResponseEntity<>("Utilisateurs supprimés", HttpStatus.ACCEPTED);
		}
	  
	  public ResponseEntity<?> findAll() {
	        var it = userRepository.findAll();
	        if(it == null) {
	        	return ResponseEntity.badRequest().body("Pas d'utilisateur"); 
	        }
	        var users = new ArrayList<User>();
	        it.forEach(e -> users.add(e));
	        return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
	    }

}
