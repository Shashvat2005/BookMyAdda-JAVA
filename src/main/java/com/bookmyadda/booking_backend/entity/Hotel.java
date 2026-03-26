package com.bookmyadda.booking_backend.entity;

import java.util.List;

public class Hotel {
    private int id;                         //identification
    private String name;                    //identification
    private List<String> images;            //required
    private List<String> amenities;         //required
    private int pricePerNight;              //required
    private double rating;                  //required
    private String description;             //required

    public Hotel(int id, String name, List<String> images,
                 List<String> amenities, int pricePerNight,
                 double rating, String description) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.amenities = amenities;
        this.pricePerNight = pricePerNight;
        this.rating = rating;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getImages() {
        return images;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }
}
