package com.akash.event_service.repo;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.event_service.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByCategoryIgnoreCaseContainingAndLocationIgnoreCaseContaining(
            String category, String location, Pageable pageable);
}
