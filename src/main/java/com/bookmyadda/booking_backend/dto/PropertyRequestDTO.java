package com.bookmyadda.booking_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class PropertyRequestDTO {

    private String propertyName;
    private String propertyType;

    //Address
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    //Media
    private List<String> images;

    //Room Details
    private int roomCount; //calculate
    private List<RoomTypeDTO> roomTypes;

    //Details
    private String propertyDescription;

    private List<String> propertyAmenities;

    private String mapLink;

    private List<String> policy;

    private String GstNumber;

}
