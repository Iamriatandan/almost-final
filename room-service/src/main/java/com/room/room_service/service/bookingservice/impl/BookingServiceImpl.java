package com.room.room_service.service.bookingservice.impl;

import com.room.room_service.dto.BookingDTO;
import com.room.room_service.dto.RateResponseDto;
import com.room.room_service.entity.Booking;
import com.room.room_service.entity.BookingStatus;
import com.room.room_service.entity.Room;
import com.room.room_service.exception.RoomNotFoundException;
import com.room.room_service.fiegn.RateClient;
import com.room.room_service.mapper.BookingMapper;
import com.room.room_service.repository.BookingRepository;
import com.room.room_service.repository.RoomRepository;
import com.room.room_service.service.bookingservice.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final RateClient rateClient;

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        log.info("Creating booking for roomId: {}", bookingDTO.getRoomId());

        Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + bookingDTO.getRoomId()));

        //calling rate service to fetch rate
        RateResponseDto rate = rateClient.getRate(bookingDTO.getRoomId(),
                bookingDTO.getCheckInDate(),
                bookingDTO.getCheckOutDate(),
                bookingDTO.getNumberOfGuests());

        // Calculate total price
        long nights = ChronoUnit.DAYS.between(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());
        double totalPrice = rate.getFirstNightPrices() +
                (nights - 1) * rate.getExtensionNight();

        //booking
        Booking booking = BookingMapper.toEntity(bookingDTO);
        booking.setRoom(room);
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.BOOKED);

        Booking saved = bookingRepository.save(booking);
        return  BookingMapper.toDTO(saved);
    }

    @Override
    public List<BookingDTO> getBookingsByRoomId(Long roomId) {
        log.info("Fetching bookings for roomId: {}", roomId);
        List<Booking> bookings = bookingRepository.findByRoomRoomId(roomId);
        return bookings.stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean cancelBooking(Long bookingId) {
        log.info("Canceling booking with ID: {}", bookingId);
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);
            return true;
        }
        return false;
    }
}
