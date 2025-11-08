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
public class EventServiceApplication {
	@Autowired
	private JwtService jwtService;

	public static void main(String[] args) {
		SpringApplication.run(EventServiceApplication.class, args);
	}
}
