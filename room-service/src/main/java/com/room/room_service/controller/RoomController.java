package com.room.room_service.controller;

import com.room.room_service.dto.BookingDTO;
import com.room.room_service.dto.RoomDTO;
import com.room.room_service.service.roomservice.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    //create a room
    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        log.info("Creating new room: {}", roomDTO);
        RoomDTO created = roomService.createRoom(roomDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //get all rooms
    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        log.info("Fetching all rooms");
        List<RoomDTO> allRooms = roomService.getAllRooms();
        return new ResponseEntity<>(allRooms,HttpStatus.OK);
    }

    //get room by id
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long roomId) {
        log.info("Fetching room with ID: {}", roomId);
        RoomDTO roomDTO= roomService.getRoomById(roomId);
        return new ResponseEntity<>(roomDTO,HttpStatus.OK);
    }

    //updating room with Id
    @PutMapping("/{roomId}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long roomId, @RequestBody RoomDTO updatedRoom) {
        log.info("Updating room with ID: {} | New details: {}", roomId, updatedRoom);
        return ResponseEntity.ok(roomService.updateRoom(roomId, updatedRoom));
    }

    //delete a room with id
    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long roomId) {
        log.info("Deleting room with ID: {}", roomId);
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok("Room with ID " + roomId + " deleted successfully.");
    }

    //extra methods
    @PostMapping("/{roomId}/bookings")
    public ResponseEntity<BookingDTO> createBooking(
            @PathVariable Long roomId,
            @RequestBody BookingDTO bookingDTO) {

        log.info("Creating booking for roomId: {}", roomId);
        BookingDTO createdBooking = roomService.createBooking(roomId, bookingDTO);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @GetMapping("/{roomId}/bookings")
    public ResponseEntity<List<BookingDTO>> getBookingsByRoomId(@PathVariable Long roomId) {
        log.info("Fetching bookings for roomId: {}", roomId);
        List<BookingDTO> bookings = roomService.getBookingsByRoomId(roomId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}
