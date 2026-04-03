package com.bookmyadda.booking_backend.entity;

import com.bookmyadda.booking_backend.Enum.PropertyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "properties")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Property {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String propertyName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType propertyType;

    //Address
    @Column(nullable = false)
    private String streetAddress;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String zipCode;


    @Column(columnDefinition = "JSON")
    private String images;


    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<RoomType> roomTypes;

    @Column(nullable = false)
    private int propertyRoomCount;


    @Column(nullable = false)
    private String propertyDescription;

    @Column(columnDefinition = "JSON")
    private String propertyAmenities;


    @Column(nullable = false)
    private boolean isVerified;
    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    private String mapLink;

    private Double rating;

    @Column(columnDefinition = "JSON")
    private String policy;

    @Column(nullable = false)
    private String GstNumber;

}

