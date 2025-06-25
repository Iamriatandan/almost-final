package com.reservation.reservationservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(nullable = false, unique = true, length = 20)
    @NotBlank(message = "Reservation code is required")
    private String reservationCode;

    @NotNull(message = "Guest ID is required")
    private Long guestId;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @Column(nullable = false)
    @FutureOrPresent(message = "Check-in date must be today or in the future")
    private LocalDate checkInDate;

    @Column(nullable = false)
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Min(value = 1, message = "At least one guest is required")
    private int numOfGuests;

    @Min(value = 0, message = "Number of adults cannot be negative")
    private int numOfAdults;

    @Min(value = 0, message = "Number of children cannot be negative")
    private int numOfChildren;

    // Utility method to calculate number of nights
    public int getNumNights() {
        if (checkInDate != null && checkOutDate != null) {
            return (int) java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        }
        return 0;
    }
}
