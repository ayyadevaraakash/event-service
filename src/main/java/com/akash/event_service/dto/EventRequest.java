package com.akash.event_service.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventRequest(
        @NotBlank @Size(max=140) String title,
        @NotBlank @Size(max=60) String category,
        @NotBlank @Size(max=120) String location,
        @NotNull LocalDateTime startAt,
        @NotNull LocalDateTime endAt,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @NotNull @Min(1) Integer totalSeats,
        @NotNull @Min(0) Integer availableSeats,
        @Size(max=10000) String description,
        @Size(max=500) String imageUrl
) {}
