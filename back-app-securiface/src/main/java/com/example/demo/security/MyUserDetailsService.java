package com.example.demo.security;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.metier.User;
import com.example.demo.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired private UserRepository userRep;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> u = userRep.findByUsernameWithRole(username);

		if (!u.isPresent())
			throw new UsernameNotFoundException("login/password invalid");

		return new MyUserDetails(u.get());
	}

}