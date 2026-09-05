package com.bookmyadda.booking_backend.repository;

import com.bookmyadda.booking_backend.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomTypeRepository extends JpaRepository<RoomType, UUID> {
}
