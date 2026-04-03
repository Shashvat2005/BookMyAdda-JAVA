package com.bookmyadda.booking_backend.repository;

import com.bookmyadda.booking_backend.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
}
