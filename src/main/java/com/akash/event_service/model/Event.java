package com.akash.event_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=140)
    private String title;

    @Column(nullable=false, length=60)
    private String category;

    @Column(nullable=false, length=120)
    private String location;

    @Column(nullable=false)
    private LocalDateTime startAt;

    @Column(nullable=false)
    private LocalDateTime endAt;

    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal price;

    @Column(nullable=false)
    private Integer totalSeats;

    @Column(nullable=false)
    private Integer availableSeats;

    @Column(columnDefinition="text")
    private String description;

    private String imageUrl;

    @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

	
}
