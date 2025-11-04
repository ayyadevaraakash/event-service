package com.akash.event_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akash.event_service.dto.EventRequest;
import com.akash.event_service.dto.EventResponse;
import com.akash.event_service.model.Event;
import com.akash.event_service.repo.EventRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class EventService {
    private final EventRepository repo;

    private EventResponse toResponse(Event e) {
        return new EventResponse(
                e.getId(), e.getTitle(), e.getCategory(), e.getLocation(),
                e.getStartAt(), e.getEndAt(), e.getPrice(),
                e.getTotalSeats(), e.getAvailableSeats(),
                e.getDescription(), e.getImageUrl(),
                e.getCreatedAt(), e.getUpdatedAt()
        );
    }

    @Transactional
    public EventResponse create(EventRequest req) {
        if (req.availableSeats() > req.totalSeats())
            throw new IllegalArgumentException("availableSeats cannot exceed totalSeats");

        Event e = Event.builder()
                .title(req.title())
                .category(req.category())
                .location(req.location())
                .startAt(req.startAt())
                .endAt(req.endAt())
                .price(req.price())
                .totalSeats(req.totalSeats())
                .availableSeats(req.availableSeats())
                .description(req.description())
                .imageUrl(req.imageUrl())
                .build();
        return toResponse(repo.save(e));
    }

    @Transactional(readOnly = true)
    public Page<EventResponse> list(String category, String location, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startAt").descending());
        if ((category == null || category.isBlank()) && (location == null || location.isBlank())) {
            return repo.findAll(pageable).map(this::toResponse);
        }
        return repo.findByCategoryIgnoreCaseContainingAndLocationIgnoreCaseContaining(
                category == null ? "" : category,
                location == null ? "" : location,
                pageable
        ).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public EventResponse get(Long id) {
        return repo.findById(id).map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + id));
    }

    @Transactional
    public EventResponse update(Long id, EventRequest req) {
        Event e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + id));
        if (req.availableSeats() > req.totalSeats())
            throw new IllegalArgumentException("availableSeats cannot exceed totalSeats");
        e.setTitle(req.title());
        e.setCategory(req.category());
        e.setLocation(req.location());
        e.setStartAt(req.startAt());
        e.setEndAt(req.endAt());
        e.setPrice(req.price());
        e.setTotalSeats(req.totalSeats());
        e.setAvailableSeats(req.availableSeats());
        e.setDescription(req.description());
        e.setImageUrl(req.imageUrl());
        return toResponse(repo.save(e));
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new IllegalArgumentException("Event not found: " + id);
        repo.deleteById(id);
    }
}
