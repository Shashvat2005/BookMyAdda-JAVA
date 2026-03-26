package com.bookmyadda.booking_backend.service;

import com.bookmyadda.booking_backend.entity.Hotel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class HotelService {
    public List<Hotel> getDemoHotels() {

        return Arrays.asList(
                new Hotel(
                        1,
                        "Ocean View Resort",
                        Arrays.asList(
                                "https://picsum.photos/400/300?1",
                                "https://picsum.photos/400/300?2",
                                "https://picsum.photos/400/300?3"
                        ),
                        Arrays.asList("Free WiFi", "Pool", "Gym", "Spa"),
                        4500,
                        4.5,
                        "A beautiful seaside resort with stunning views."
                ),

                new Hotel(
                        2,
                        "Mountain Escape Hotel",
                        Arrays.asList(
                                "https://picsum.photos/400/300?4",
                                "https://picsum.photos/400/300?5",
                                "https://picsum.photos/400/300?6"
                        ),
                        Arrays.asList("Free Parking", "Breakfast Included", "Hiking Trails"),
                        3200,
                        4.2,
                        "Perfect getaway in the mountains for relaxation."
                ),

                new Hotel(
                        3,
                        "City Luxury Suites",
                        Arrays.asList(
                                "https://picsum.photos/400/300?7",
                                "https://picsum.photos/400/300?8",
                                "https://picsum.photos/400/300?9"
                        ),
                        Arrays.asList("WiFi", "AC", "Room Service", "Bar"),
                        6000,
                        4.8,
                        "Premium luxury stay in the heart of the city."
                )
        );
    }
}
