package com.bookmyadda.booking_backend.controller;

import com.bookmyadda.booking_backend.entity.Hotel;
import com.bookmyadda.booking_backend.service.HotelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@CrossOrigin(origins = "*")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/demo")
    public List<Hotel> getDemoHotels() {
        return hotelService.getDemoHotels();
    }
}
