package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.dto.clientinfodto.GuestInfoDto;
import com.reservation.reservationservice.dto.clientinfodto.RoomInfoDto;
import com.reservation.reservationservice.entity.ReservationStatus;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ReservationResponseDTO {
    //data that will be fetched while get request
    private Long reservationId;
    private String reservationCode;
    private GuestInfoDto guestInfo;
    private RoomInfoDto roomInfo;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ReservationStatus status;
    private int  numOfGuests;
    private int numOfAdults;
    private int numOfChildren;
}
