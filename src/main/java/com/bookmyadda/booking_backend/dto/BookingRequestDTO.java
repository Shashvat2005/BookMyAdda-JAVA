package com.bookmyadda.booking_backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BookingRequestDTO {
    private UUID roomTypeId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int roomsBooked;
}
