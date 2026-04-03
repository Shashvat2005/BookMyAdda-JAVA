package com.bookmyadda.booking_backend.controller;

import com.bookmyadda.booking_backend.dto.PropertyRequestDTO;
import com.bookmyadda.booking_backend.entity.Property;
import com.bookmyadda.booking_backend.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*")
public class PropertyController {
    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // REGISTER PROPERTY
    @PostMapping("/register")
    public ResponseEntity<?> registerProperty(@RequestBody PropertyRequestDTO dto) {
        try {
            Property savedProperty = propertyService.registerProperty(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProperty);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong while registering property");
        }
    }

    // GET ALL PROPERTIES
    @GetMapping
    public ResponseEntity<?> getAllProperties() {
        try {
            List<Property> properties = propertyService.getAllProperties();

            if (properties.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No properties found");
            }

            return ResponseEntity.ok(properties);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong while fetching properties");
        }
    }

    @GetMapping("/test")
    public String test(){
        return "Working fine";
    }
}
