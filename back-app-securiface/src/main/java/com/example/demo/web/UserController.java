package com.example.demo.web;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.metier.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("users")
@CrossOrigin("http://localhost:4200")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	  @GetMapping("/")
	  @ResponseBody
	  public ResponseEntity<?> getAllUsers() {
		return userService.findAll();
	  }

	  @PostMapping("/create")
	  @ResponseBody
	  public String createUser(@RequestBody User user) {
		    try {
		      userService.create(user);
		    }
		    catch (Exception ex) {
		      return "Erreur création de l'utilisateur: " + ex.toString();
		    }
		    return "Utilisateur créé";
	  }
	  
	  @GetMapping("/{id}")
	  @ResponseBody
	  public ResponseEntity<?> findOne(@PathVariable Long id) {
	      System.out.println(id);
	      return userService.findById(id);
	  }
	  
	  @PostMapping("/login")
	  @ResponseBody
	  public ResponseEntity<?> login(@RequestBody User user) {
	      return userService.findByEmailAndPassword(user);
	  }
	  
	  /*
	  @PostMapping("/login")
	  @ResponseBody
	  public Optional<User> login(@RequestBody User user) {
		  Optional<User> userSelected = null;
		  try {
			  // ObjectMapper mapper = new ObjectMapper();
			  // Map<String, Object> response = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
	            // });
		        if (!user.isEmpty()) {
		        	// System.out.println("user " + response.get("email"));
		  	      userSelected = userService.findByEmailAndPassword(user);
		        }
		    } catch (Exception ex) {
		    	log.info("Cannot parse JSON for spring.application.json: ", ex);
		    }
		  return userSelected;
	  }
	  */
	  @PutMapping("/update")
	  @ResponseBody
	  public String updateUser(@RequestBody User user) {
		    try {
		      userService.update(user);
		    }
		    catch (Exception ex) {
		      return "Erreur maj de l'utilisateur: " + ex.toString();
		    }
		    return "Utilisateur mis à jour";
	  }
	  
	  @PutMapping("/updatePassword")
	  @ResponseBody
	  public ResponseEntity<?> updateUserPswd(@RequestBody User user) {
		    try {
		      return userService.updatePswd(user);
		    }
		    catch (Exception ex) {
		      return new ResponseEntity<>("Erreur maj de l'utilisateur: " + ex.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	  }
	  
	  @DeleteMapping("/delete/{id}")
	  @ResponseBody
	  public String deleteUser(@PathVariable Long id) {
		    try {
		      userService.delete(id);
		    }
		    catch (Exception ex) {
		      return "Erreur suppression de l'utilisateur: " + ex.toString();
		    }
		    return "Utilisateur supprimé";
	  }
	  
	  @DeleteMapping("/deleteAll")
	  @ResponseBody
	  public String deleteUsers() {
		    try {
		      userService.deleteAll();
		    }
		    catch (Exception ex) {
		      return "Erreur suppression des utilisateurs";
		    }
		    return "Utilisateurs supprimés";
	  }

	
}
