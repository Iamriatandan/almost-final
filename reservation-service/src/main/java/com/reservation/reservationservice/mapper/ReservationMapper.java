package com.reservation.reservationservice.mapper;

import com.reservation.reservationservice.dto.ReservationRequestDTO;
import com.reservation.reservationservice.dto.ReservationResponseDTO;
import com.reservation.reservationservice.dto.clientinfodto.GuestInfoDto;
import com.reservation.reservationservice.dto.clientinfodto.RoomInfoDto;
import com.reservation.reservationservice.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    // mapping dto to entity
    public static Reservation toEntity(ReservationRequestDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setGuestId(dto.getGuestId());
        reservation.setRoomId(dto.getRoomId());
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());
        reservation.setNumOfGuests(dto.getNumOfAdults() + dto.getNumOfChildren());
        return reservation;
    }

    // mapping entity to dto with nested guest and room info
    public static ReservationResponseDTO toDto(Reservation reservation, GuestInfoDto guestInfoDto, RoomInfoDto roomInfoDto) {
        return ReservationResponseDTO.builder()
                .reservationId(reservation.getReservationId())
                .reservationCode(reservation.getReservationCode())
                .guestInfo(guestInfoDto)
                .roomInfo(roomInfoDto)
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .status(reservation.getStatus())
                .numOfAdults(reservation.getNumOfAdults())
                .numOfChildren(reservation.getNumOfChildren())
                .numOfGuests(reservation.getNumOfGuests())
                .build();
    }
}
