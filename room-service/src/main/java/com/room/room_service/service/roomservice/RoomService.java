package com.room.room_service.service.roomservice;

import com.room.room_service.dto.BookingDTO;
import com.room.room_service.dto.RoomDTO;
import com.room.room_service.entity.RoomStatus;

import java.util.List;

public interface RoomService {
    //main crud operations
    RoomDTO createRoom(RoomDTO roomDTO);

    List<RoomDTO> getAllRooms();

    RoomDTO getRoomById(Long roomId);

    RoomDTO updateRoom(Long roomId, RoomDTO roomDTO);

    boolean deleteRoom(Long roomId);

    //booking methods
    BookingDTO createBooking(Long roomId, BookingDTO bookingDTO);

    List<BookingDTO> getBookingsByRoomId(Long roomId);
}
