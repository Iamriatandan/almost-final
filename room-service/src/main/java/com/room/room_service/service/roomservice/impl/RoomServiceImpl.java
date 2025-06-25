package com.room.room_service.service.roomservice.impl;

import com.room.room_service.dto.BookingDTO;
import com.room.room_service.dto.RoomDTO;
import com.room.room_service.entity.Booking;
import com.room.room_service.entity.Room;
import com.room.room_service.entity.RoomStatus;
import com.room.room_service.exception.DuplicateRoomNumberException;
import com.room.room_service.exception.RoomNotFoundException;
import com.room.room_service.mapper.BookingMapper;
import com.room.room_service.mapper.RoomMapper;
import com.room.room_service.repository.BookingRepository;
import com.room.room_service.repository.RoomRepository;
import com.room.room_service.service.roomservice.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final BookingRepository bookingRepository;

    //create a room
    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) {
        //if room already exists
        if (roomRepository.existsByRoomNumber(roomDTO.getRoomNumber())) {
            log.error("Room number already exists: {}", roomDTO.getRoomNumber());
            throw new DuplicateRoomNumberException("Room with number " + roomDTO.getRoomNumber() + " already exists.");
        }

        Room room = RoomMapper.toEntity(roomDTO);
        Room saved = roomRepository.save(room);

        log.info("Created new room with ID: {}", room.getRoomId());

        return roomMapper.toRoomDTO(room);
    }

    //get all rooms
    @Override
    public List<RoomDTO> getAllRooms() {
        log.info("Fetching all rooms...");
        return roomRepository.findAll()
                .stream()
                .map(RoomMapper::toRoomDTO)
                .collect(Collectors.toList());
    }

    //get room by id
    @Override
    public RoomDTO getRoomById(Long roomId) {
        log.info("Fetching room with ID: {}", roomId);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> {
                    log.error("Room not found with ID: {}", roomId);
                    return new RoomNotFoundException("Room not found with ID: " + roomId);
                });
        return roomMapper.toRoomDTO(room);
    }

    //update room
    @Override
    public RoomDTO updateRoom(Long roomId, RoomDTO roomDTO) {
        log.info("Updating room with ID: {}", roomId);
        // Check if the room exists
        Room existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> {
                    log.error("Room not found with ID: {}", roomId);
                    return new RoomNotFoundException("Room not found with ID: " + roomId);
                });
        existingRoom.setRoomNumber(roomDTO.getRoomNumber());
        existingRoom.setType(roomDTO.getType());
        existingRoom.setDescription(roomDTO.getDescription());
        existingRoom.setPricePerNight(roomDTO.getPricePerNight());
        existingRoom.setStatus(roomDTO.getStatus());

        existingRoom = roomRepository.save(existingRoom);

        log.info("Updated room with ID: {}", existingRoom.getRoomId());
        return roomMapper.toRoomDTO(existingRoom);
    }

    //delete a room
    @Override
    public boolean deleteRoom(Long roomId) {
        log.info("Deleting room with ID: {}", roomId);
        Room existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> {
                    log.error("Room not found with ID: {}", roomId);
                    return new RoomNotFoundException("Room not found with ID: " + roomId);
                });

        // Delete the room from the repository
        roomRepository.delete(existingRoom);

        log.info("Deleted room with ID: {}", roomId);
        return true;
    }
    public RoomDTO updateRoomStatus(Long roomId, RoomStatus status) {
        log.info("Updating room status for room with ID: {}", roomId);
        // Find room by ID
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> {
                    log.error("Room not found with ID: {}", roomId);
                    return new RoomNotFoundException("Room not found with ID: " + roomId);
                });

        room.setStatus(status);
        room = roomRepository.save(room);

        log.info("Updated room status for room ID: {}", roomId);
        return roomMapper.toRoomDTO(room);
    }

    @Override
    public BookingDTO createBooking(Long roomId, BookingDTO bookingDTO) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

        Booking booking = BookingMapper.toEntity(bookingDTO);
        booking.setRoom(room);

        // Optional: You can add date conflict checks here

        Booking savedBooking = bookingRepository.save(booking);
        return BookingMapper.toDTO(savedBooking);
    }

    @Override
    public List<BookingDTO> getBookingsByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

        List<Booking> bookings = room.getBookings();
        return bookings.stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
    }

}
