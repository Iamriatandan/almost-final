package com.room.room_service.dto;

import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateResponseDto {
    private Long rateId;
    private LocalDate checkInDate;
    private LocalDate checkoutDate;
    private String Day;
    private int numberOfGuests;
    private double firstNightPrices;
    private double extensionNight;
    private Long roomId;
}
