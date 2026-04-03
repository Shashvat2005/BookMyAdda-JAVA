package com.bookmyadda.booking_backend.entity;

import com.bookmyadda.booking_backend.Enum.BedType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomType {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id; // Room ID

    private String type;

    @Column(columnDefinition = "JSON")
    private String images;

    @Column(nullable = false)
    private double roomSize;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BedType bedType;
    @Column(nullable = false)
    private int occupancy;
    @Column(nullable = false)
    private int pricingType; // 0 -> per night. 1 -> per person

    @Column(nullable = false)
    private int count;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int numberOfBeds;

    @Column(columnDefinition = "JSON")
    private String roomAmenities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;
}
