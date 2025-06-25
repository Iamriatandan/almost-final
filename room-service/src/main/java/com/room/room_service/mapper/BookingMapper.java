package com.room.room_service.mapper;

import com.room.room_service.dto.BookingDTO;
import com.room.room_service.entity.Booking;
import com.room.room_service.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    //entity to dto
    public static BookingDTO toDTO(Booking booking) {
        if (booking == null) return null;

        return BookingDTO.builder()
                .bookingId(booking.getBookingId())
                .roomId(booking.getRoom().getRoomId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus())
                .build();
    }

    public static Booking toEntity(BookingDTO dto) {
        if (dto == null) return null;

        return Booking.builder()
                .checkInDate(dto.getCheckInDate())
                .checkOutDate(dto.getCheckOutDate())
                .totalPrice(dto.getTotalPrice())
                .status(dto.getStatus())
                .room(Room.builder().roomId(dto.getRoomId()).build()) // only ID reference
                .build();
    }
}
