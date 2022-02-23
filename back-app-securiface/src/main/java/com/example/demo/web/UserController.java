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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.metier.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("users")
@CrossOrigin("http://localhost:4200")
public class UserController {

	@Autowired
	private UserRepository userRep;

	@GetMapping
	public ResponseEntity<Page<User>> listUsers(@PageableDefault(page = 0, size = 10) Pageable page) {

		Page<User> users = userRep.findAll(page);

		if (users.isEmpty())
			System.err.println("Rien ne vas plus ! La BDD est vide !");

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{id:[0-9]+}")
	public ResponseEntity<User> findById(@PathVariable("id") int id) {
		Optional<User> opUser = userRep.findById(id);

		if (!opUser.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(opUser.get(), HttpStatus.OK);
	}

	@GetMapping("/login")
	public ResponseEntity<User> login(Principal principal) {
		return this.userRep.findByUsername(principal.getName()).map(u -> new ResponseEntity<>(u, HttpStatus.ACCEPTED))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}
