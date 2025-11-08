package com.akash.event_service.booking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akash.event_service.auth.model.User;
import com.akash.event_service.auth.repo.UserRepository;
import com.akash.event_service.booking.dto.BookingRequest;
import com.akash.event_service.booking.model.Booking;
import com.akash.event_service.booking.repo.BookingRepository;
import com.akash.event_service.model.Event;
import com.akash.event_service.repo.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

	private final BookingRepository bookingRepo;
	private final UserRepository userRepo;
	private final EventRepository eventRepo;

	@Transactional
	public Booking bookEvent(String email, BookingRequest req) {
		User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		Event event = eventRepo.findById(req.getEventId()).orElseThrow(() -> new RuntimeException("Event not found"));

		// Check available seats before booking
		if (event.getAvailableSeats() < req.getSeats()) {
			throw new RuntimeException("Not enough seats available!");
		}

		// Deduct seats and update event
		event.setAvailableSeats(event.getAvailableSeats() - req.getSeats());
		eventRepo.save(event);

		// Create booking record
		Booking booking = Booking.builder().user(user).event(event).seats(req.getSeats()).bookedAt(LocalDateTime.now())
				.build();

		return bookingRepo.save(booking);
	}

	public List<Booking> getUserBookings(String email) {
		User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		return bookingRepo.findByUser(user);
	}
}
