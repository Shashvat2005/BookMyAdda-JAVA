package com.bookmyadda.booking_backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BookingResponseDTO {
    private UUID id;
    private String propertyName;
    private String roomType;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int roomsBooked;
    private int totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
