package com.bookmyadda.booking_backend.service;

import com.bookmyadda.booking_backend.Enum.BedType;
import com.bookmyadda.booking_backend.Enum.PropertyType;
import com.bookmyadda.booking_backend.dto.PropertyRequestDTO;
import com.bookmyadda.booking_backend.entity.Property;
import com.bookmyadda.booking_backend.entity.RoomType;
import com.bookmyadda.booking_backend.repository.PropertyRepository;
import jakarta.persistence.Column;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import com.bookmyadda.booking_backend.utils.Helper;
import java.util.List;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final ObjectMapper objectMapper;
    private final Helper helper;

    public PropertyService(PropertyRepository propertyRepository, ObjectMapper objectMapper, Helper helper) {
        this.propertyRepository = propertyRepository;
        this.objectMapper = objectMapper;
        this.helper = helper;
    }

    public Property registerProperty(PropertyRequestDTO propertyRequestDTO){
        validateProperty(propertyRequestDTO);
        Property property = convertToEntity(propertyRequestDTO);
        return propertyRepository.save(property);
    }

    public void validateProperty(PropertyRequestDTO propertyRequestDTO){
        if (propertyRequestDTO.getPropertyName() == null || propertyRequestDTO.getPropertyName().isBlank()) {
            throw new RuntimeException("Property name is required");
        }

        if (propertyRequestDTO.getPropertyType() == null) {
            throw new RuntimeException("Property type is required");
        }

        if (propertyRequestDTO.getRoomTypes() == null || propertyRequestDTO.getRoomTypes().isEmpty()) {
            throw new RuntimeException("At least one room type is required");
        }
        // Add gst also
    }

    public Property convertToEntity(PropertyRequestDTO dto){
        Property property = new Property();

        property.setPropertyName(dto.getPropertyName());
        try {
            property.setPropertyType(
                    PropertyType.valueOf(dto.getPropertyType().trim().toUpperCase())
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid property type");
        }

        // Address
        property.setStreetAddress(dto.getStreetAddress());
        property.setCity(dto.getCity());
        property.setState(dto.getState());
        property.setZipCode(dto.getZipCode());

        // JSON fields
        property.setImages(helper.toJson(dto.getImages()));
        property.setPropertyAmenities(helper.toJson(dto.getPropertyAmenities()));
        property.setPolicy(helper.toJson(dto.getPolicy()));

        property.setPropertyDescription(dto.getPropertyDescription());
        property.setMapLink(dto.getMapLink());
        property.setGstNumber(dto.getGstNumber());

        // Backend controlled
        property.setVerified(false);
        property.setDeleted(false);
        property.setRating(0.0);

        // Room Types
        List<RoomType> roomTypes = dto.getRoomTypes().stream().map(rt -> {

            RoomType room = new RoomType();

            room.setType(rt.getRoomType()); // still string (fine for now)

            room.setRoomSize(rt.getRoomSize());

            room.setBedType(BedType.valueOf(rt.getBedType().trim().toUpperCase()));
            room.setOccupancy(rt.getOccupancy());
            room.setPricingType(rt.getPricingType());


            room.setImages(helper.toJson(rt.getImages()));
            room.setCount(rt.getCount());
            room.setPrice(rt.getPrice());

            room.setRoomAmenities(helper.toJson(rt.getAmenities()));

            // VERY IMPORTANT
            room.setProperty(property);

            return room;

        }).toList();

        property.setRoomTypes(roomTypes);

        // Calculate total rooms
        int totalRooms = roomTypes.stream()
                .mapToInt(RoomType::getCount)
                .sum();

        property.setPropertyRoomCount(totalRooms);

        return property;

    }

    public List<Property> getAllProperties(){
        return propertyRepository.findAll();
    }

}
