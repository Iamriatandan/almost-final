package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.ReservationRequestDTO;
import com.reservation.reservationservice.dto.ReservationResponseDTO;
import com.reservation.reservationservice.entity.ReservationStatus;
import com.reservation.reservationservice.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;


    //Get all reservations
    @GetMapping("/allReservations")
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        log.info("Fetching all reservations");
        List<ReservationResponseDTO> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    // Create a new reservation
    @PostMapping("/createReservation")
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        log.info("creating reservation for guest : {} ", reservationRequestDTO.getGuestId());
        ReservationResponseDTO created = reservationService.createReservation(reservationRequestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get a reservation by ID
    @GetMapping("/getReservationById/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable Long id) {
        log.info("Fetching reservation by ID : {}",id);
        ReservationResponseDTO reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    //  Update reservation
    @PutMapping("/updateReservationById/{id}")
    public ResponseEntity<ReservationResponseDTO> updateReservation(
            @PathVariable Long id,
            @RequestBody ReservationRequestDTO updatedDTO) {
        log.info("Updating reservation with id: {}",id);
        ReservationResponseDTO updated = reservationService.updateReservation(id, updatedDTO);
        return ResponseEntity.ok(updated);
    }

    //  Delete reservation
    @DeleteMapping("/deleteReservationById/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.info("Deleting reservation with ID: {}",id);
        boolean deleted = reservationService.deleteReservation(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    //  Get by guest ID
    @GetMapping("/guestById/{guestId}")
    public ResponseEntity<List<ReservationResponseDTO>> getByGuestId(@PathVariable Long guestId) {
        log.info("Fetching reservations for guest ID: {}", guestId);
        return ResponseEntity.ok(reservationService.getReservationByGuestId(guestId));
    }

    //  Get by room ID
    @GetMapping("/roomById/{roomId}")
    public ResponseEntity<List<ReservationResponseDTO>> getByRoomId(@PathVariable Long roomId) {
        log.info("Fetching reservations for room ID: {}", roomId);
        return ResponseEntity.ok(reservationService.getReservationByRoomId(roomId));
    }

    // Get by reservation status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReservationResponseDTO>> getByStatus(@PathVariable ReservationStatus status) {
        log.info("Fetching reservations with status: {}", status);
        return ResponseEntity.ok(reservationService.getReservationByStatus(status));
    }
}
