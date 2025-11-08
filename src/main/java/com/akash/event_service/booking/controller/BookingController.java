package com.akash.event_service.booking.controller;

import com.akash.event_service.booking.dto.BookingRequest;
import com.akash.event_service.booking.model.Booking;
import com.akash.event_service.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingController {

	private final BookingService bookingService;

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Booking createBooking(@RequestBody BookingRequest req, Authentication auth) {
		System.out.println("post user bookings");
		return bookingService.bookEvent(auth.getName(), req);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public List<Booking> getUserBookings(Authentication auth) {
		System.out.println("get user bookings");
		return bookingService.getUserBookings(auth.getName());
	}

	@GetMapping("/test")
	public String test(Authentication auth) {
		return "Authenticated as: " + auth.getName() + " with roles: " + auth.getAuthorities();
	}
}
