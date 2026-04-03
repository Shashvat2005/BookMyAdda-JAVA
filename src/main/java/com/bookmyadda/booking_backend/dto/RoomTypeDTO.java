package com.bookmyadda.booking_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomTypeDTO {

    private String roomType;
    private int count;
    private int price;
    private int numberOfBeds;
    private String bedType;
    private int occupancy;
    private int pricingType; // 0 -> per night. 1 -> per person
    private double roomSize; //sqmtr
    private List<String> amenities;
    private List<String> images;

}
