package com.bookmyadda.booking_backend.repository;

import com.bookmyadda.booking_backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query("SELECT COALESCE(SUM(b.roomsBooked), 0) FROM Booking b " +
            "WHERE b.roomType.id = :roomTypeId " +
            "AND b.status IN (com.bookmyadda.booking_backend.Enum.BookingStatus.PENDING, " +
            "com.bookmyadda.booking_backend.Enum.BookingStatus.CONFIRMED) " +
            "AND b.checkIn < :checkOut AND b.checkOut > :checkIn")
    int countOverlappingRooms(@Param("roomTypeId") UUID roomTypeId,
                              @Param("checkIn") LocalDate checkIn,
                              @Param("checkOut") LocalDate checkOut);

    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);
}
