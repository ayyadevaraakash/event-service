package com.akash.event_service.controller;

import com.akash.event_service.auth.model.User;
import com.akash.event_service.auth.repo.UserRepository;
import com.akash.event_service.booking.model.Booking;
import com.akash.event_service.booking.repo.BookingRepository;
import com.akash.event_service.model.Event;
import com.akash.event_service.repo.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserRepository userRepo;
    private final EventRepository eventRepo;
    private final BookingRepository bookingRepo;

    // --- Dashboard Stats ---
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> getStats() {
        return Map.of(
                "totalUsers", userRepo.count(),
                "totalEvents", eventRepo.count(),
                "totalBookings", bookingRepo.count()
        );
    }

    // --- Read All ---
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/events")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    @GetMapping("/bookings")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    // --- Delete ---
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
    }

    @DeleteMapping("/events/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteEvent(@PathVariable Long id) {
        eventRepo.deleteById(id);
    }

    @DeleteMapping("/bookings/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBooking(@PathVariable Long id) {
        bookingRepo.deleteById(id);
    }
}
