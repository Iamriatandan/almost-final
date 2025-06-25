package com.room.room_service.repository;

import com.room.room_service.entity.Booking;
import com.room.room_service.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByRoomRoomId(Long roomId);

    List<Booking> findByRoomRoomIdAndCheckOutDateAfterAndCheckInDateBefore(
            Long roomId, LocalDate checkInDate, LocalDate checkOutDate
    );
    List<Booking> findByRoom(Room room);
}
