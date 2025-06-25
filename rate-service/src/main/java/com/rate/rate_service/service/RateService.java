package com.rate.rate_service.service;

import com.rate.rate_service.dto.RateDTO;
import com.rate.rate_service.entity.Rate;

import java.time.LocalDate;
import java.util.List;

public interface RateService {
    RateDTO createRate(RateDTO rateDTO);

    List<RateDTO> getAllRates();

    RateDTO getRateById(Long rateId);

    RateDTO updateRate(Long rateId, RateDTO rateDTO);

    boolean deleteRate(Long rateId);
    List<RateDTO> getRatesByRoomId(Long roomId);

    //inter service to fetch rate
    Rate getRateFor(Long roomId, LocalDate checkIn, LocalDate checkOut, int guests);
}
