package com.bookmyadda.booking_backend.service;

import com.bookmyadda.booking_backend.Enum.BookingStatus;
import com.bookmyadda.booking_backend.dto.BookingRequestDTO;
import com.bookmyadda.booking_backend.dto.BookingResponseDTO;
import com.bookmyadda.booking_backend.entity.Booking;
import com.bookmyadda.booking_backend.entity.RoomType;
import com.bookmyadda.booking_backend.entity.User;
import com.bookmyadda.booking_backend.repository.BookingRepository;
import com.bookmyadda.booking_backend.repository.RoomTypeRepository;
import com.bookmyadda.booking_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomTypeRepository roomTypeRepository;

    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          RoomTypeRepository roomTypeRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO dto, String email) {

        validateBookingRequest(dto);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RoomType roomType = roomTypeRepository.findById(dto.getRoomTypeId())
                .orElseThrow(() -> new RuntimeException("Room type not found"));

        int alreadyBooked = bookingRepository.countOverlappingRooms(
                roomType.getId(), dto.getCheckIn(), dto.getCheckOut()
        );

        int available = roomType.getCount() - alreadyBooked;

        if (dto.getRoomsBooked() > available) {
            throw new RuntimeException(
                    "Only " + available + " room(s) available for the selected dates"
            );
        }

        int nights = (int) ChronoUnit.DAYS.between(dto.getCheckIn(), dto.getCheckOut());

        // pricingType 0 -> per night, 1 -> per person
        int totalPrice = roomType.getPrice() * nights * dto.getRoomsBooked();
        if (roomType.getPricingType() == 1) {
            totalPrice = totalPrice * roomType.getOccupancy();
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoomType(roomType);
        booking.setCheckIn(dto.getCheckIn());
        booking.setCheckOut(dto.getCheckOut());
        booking.setRoomsBooked(dto.getRoomsBooked());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setCreatedAt(LocalDateTime.now());

        return convertToResponse(bookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getMyBookings(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookingResponseDTO getBookingById(UUID id, String email) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        checkOwnership(booking, email);

        return convertToResponse(booking);
    }

    @Transactional
    public BookingResponseDTO cancelBooking(UUID id, String email) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        checkOwnership(booking, email);

        if (booking.getStatus() != BookingStatus.PENDING
                && booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException(
                    "Only pending or confirmed bookings can be cancelled"
            );
        }

        booking.setStatus(BookingStatus.CANCELLED);

        return convertToResponse(bookingRepository.save(booking));
    }

    private void validateBookingRequest(BookingRequestDTO dto) {

        if (dto.getRoomTypeId() == null) {
            throw new RuntimeException("Room type id is required");
        }

        if (dto.getCheckIn() == null) {
            throw new RuntimeException("Check-in date is required");
        }

        if (dto.getCheckIn().isBefore(LocalDate.now())) {
            throw new RuntimeException("Check-in date cannot be in the past");
        }

        if (dto.getCheckOut() == null || !dto.getCheckOut().isAfter(dto.getCheckIn())) {
            throw new RuntimeException("Check-out date must be after check-in date");
        }

        if (dto.getRoomsBooked() < 1) {
            throw new RuntimeException("At least one room must be booked");
        }
    }

    private void checkOwnership(Booking booking, String email) {
        if (booking.getUser() == null
                || !booking.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You are not allowed to access this booking");
        }
    }

    private BookingResponseDTO convertToResponse(Booking booking) {

        RoomType roomType = booking.getRoomType();

        return BookingResponseDTO.builder()
                .id(booking.getId())
                .propertyName(
                        roomType != null && roomType.getProperty() != null
                                ? roomType.getProperty().getPropertyName()
                                : null
                )
                .roomType(roomType != null ? roomType.getType() : null)
                .checkIn(booking.getCheckIn())
                .checkOut(booking.getCheckOut())
                .roomsBooked(booking.getRoomsBooked())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus().name())
                .createdAt(booking.getCreatedAt())
                .build();
    }
}
