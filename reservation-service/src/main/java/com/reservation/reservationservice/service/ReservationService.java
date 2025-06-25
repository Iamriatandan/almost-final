package com.reservation.reservationservice.service;

import com.reservation.reservationservice.dto.ReservationRequestDTO;
import com.reservation.reservationservice.dto.ReservationResponseDTO;
import com.reservation.reservationservice.entity.Reservation;
import com.reservation.reservationservice.entity.ReservationStatus;

import java.util.List;

public interface ReservationService {
    //methods with reservation endpoints
    List<ReservationResponseDTO> getAllReservations();
    ReservationResponseDTO createReservation(ReservationRequestDTO dto);
    ReservationResponseDTO getReservationById(Long reservationId);
    ReservationResponseDTO updateReservation(Long reservationId, ReservationRequestDTO dto);
    boolean deleteReservation(Long reservationId);

    //methods with other parameter endpoints
    List<ReservationResponseDTO> getReservationByGuestId(Long guestId);
    List<ReservationResponseDTO> getReservationByRoomId(Long roomId);
    List<ReservationResponseDTO> getReservationByStatus(ReservationStatus status);

}
