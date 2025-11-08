package com.akash.event_service.booking.repo;

import com.akash.event_service.booking.model.Booking;
import com.akash.event_service.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
}
