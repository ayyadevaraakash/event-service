package com.akash.event_service.booking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    private Long eventId;
    private int seats;
}
