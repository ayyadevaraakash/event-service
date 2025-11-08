package com.akash.event_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.akash.event_service.auth.model.Role;
import com.akash.event_service.auth.model.User;
import com.akash.event_service.auth.repo.UserRepository;
import com.akash.event_service.auth.service.JwtService;
import com.akash.event_service.model.Event;

@SpringBootApplication
public class EventServiceApplication implements CommandLineRunner {
	@Autowired
	private JwtService jwtService;

	public static void main(String[] args) {
		SpringApplication.run(EventServiceApplication.class, args);		
	}
	


	@Override
	public void run(String... args) {
	    String token = jwtService.generateToken("akash@gmail.com");
	    System.out.println("Generated Token: " + token);
	    System.out.println("Extracted: " + jwtService.extractUsername(token));
	}


}
