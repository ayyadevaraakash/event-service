package com.akash.event_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.akash.event_service.model.Event;

@SpringBootApplication
public class EventServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventServiceApplication.class, args);
		Event e = new Event();
		e.setTitle("Demo");
		System.out.println(e.getTitle());

	}

}
