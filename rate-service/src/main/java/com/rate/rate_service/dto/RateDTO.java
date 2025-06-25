package com.rate.rate_service.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateDTO {
    private Long rateId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String day;
    private int numberOfGuests;
    private double firstNightPrice;
    private double extensionNightPrice;
    private Long roomId;
}
