package com.rate.rate_service.mapper;

import com.rate.rate_service.dto.RateDTO;
import com.rate.rate_service.entity.Rate;

public class RateMapper {

    // Convert Entity to DTO
    public static RateDTO toDto(Rate rate){
     if (rate == null) return null;
        return RateDTO.builder()
                .rateId(rate.getRateId())
            .roomId(rate.getRoomId())
            .checkInDate(rate.getCheckInDate())
            .checkOutDate(rate.getCheckOutDate())
            .day(rate.getDay())
            .numberOfGuests(rate.getNumberOfGuests())
            .firstNightPrice(rate.getFirstNightPrice())
            .extensionNightPrice(rate.getExtensionNightPrice())
            .build();
}

    public static Rate toEntity(RateDTO rateDTO) {
        if (rateDTO == null) return null;

        return Rate.builder()
                .rateId(rateDTO.getRateId())
                .roomId(rateDTO.getRoomId())
                .checkInDate(rateDTO.getCheckInDate())
                .checkOutDate(rateDTO.getCheckOutDate())
                .day(rateDTO.getDay())
                .numberOfGuests(rateDTO.getNumberOfGuests())
                .firstNightPrice(rateDTO.getFirstNightPrice())
                .extensionNightPrice(rateDTO.getExtensionNightPrice())
                .build();
    }
}
