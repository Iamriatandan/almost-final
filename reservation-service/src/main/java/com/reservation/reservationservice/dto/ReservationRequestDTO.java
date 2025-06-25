package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.entity.ReservationStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
    //keeps only necessary data while post request
    @NotNull(message = "Guest ID cannot be null")
    private Long guestId;

    @NotNull(message = "Room ID cannot be null")
    private Long roomId;

    @FutureOrPresent(message = "Check-in date cannot be in the past")
    private LocalDate checkInDate;

    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "At least one adult must be present")
    private int numOfAdults;

    @Min(value = 0, message = "Children count cannot be negative")
    private int numOfChildren;

}
