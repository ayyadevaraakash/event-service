package com.akash.event_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String category,
        String location,
        LocalDateTime startAt,
        LocalDateTime endAt,
        BigDecimal price,
        Integer totalSeats,
        Integer availableSeats,
        String description,
        String imageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
