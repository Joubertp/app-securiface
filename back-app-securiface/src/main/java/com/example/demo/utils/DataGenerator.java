package com.example.demo.utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.demo.repository.UserRepository;

@Controller
public class DataGenerator {

	private Random random = new Random();
	
	@Autowired 	private UserRepository userRep;
	

	
}