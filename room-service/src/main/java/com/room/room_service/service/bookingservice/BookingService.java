package com.room.room_service.service.bookingservice;

import com.room.room_service.dto.BookingDTO;

import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingDTO bookingDTO);

    List<BookingDTO> getBookingsByRoomId(Long roomId);

    boolean cancelBooking(Long bookingId);
}
