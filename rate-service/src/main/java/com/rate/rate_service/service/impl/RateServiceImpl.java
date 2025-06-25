package com.rate.rate_service.service.impl;

import com.rate.rate_service.dto.RateDTO;
import com.rate.rate_service.entity.Rate;
import com.rate.rate_service.exception.RateNotFoundException;
import com.rate.rate_service.mapper.RateMapper;
import com.rate.rate_service.repository.RateRepository;
import com.rate.rate_service.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
@Service
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;

    //create rate
    @Override
    public RateDTO createRate(RateDTO rateDTO) {
        log.info("Creating rate fir roomId : {} " ,rateDTO.getRoomId());

        Rate rate = RateMapper.toEntity(rateDTO);
        Rate saved = rateRepository.save(rate);
        return RateMapper.toDto(saved);
    }

    //get all rates
    @Override
    public List<RateDTO> getAllRates() {

        return rateRepository.findAll()
                .stream()
                .map(RateMapper::toDto)
                .collect(Collectors.toList());
    }

   // get specific rate by id
    @Override
    public RateDTO getRateById(Long rateId) {
        Rate rate = rateRepository.findById(rateId)
                .orElseThrow(() -> new RateNotFoundException("Rate with ID " + rateId + " not found."));
        return RateMapper.toDto(rate);
    }

    //update existing rate
    @Override
    public RateDTO updateRate(Long rateId, RateDTO rateDTO) {
        Rate rate = rateRepository.findById(rateId)
                .orElseThrow(() -> new RateNotFoundException("Rate with ID " + rateId + " not found."));

        rate.setCheckInDate(rateDTO.getCheckInDate());
        rate.setCheckOutDate(rateDTO.getCheckOutDate());
        rate.setDay(rateDTO.getDay());
        rate.setNumberOfGuests(rateDTO.getNumberOfGuests());
        rate.setFirstNightPrice(rateDTO.getFirstNightPrice());
        rate.setExtensionNightPrice(rateDTO.getExtensionNightPrice());

        return RateMapper.toDto(rateRepository.save(rate));
    }

    //delete rate
    @Override
    public boolean deleteRate(Long rateId) {
        if (!rateRepository.existsById(rateId)) {
            throw new RateNotFoundException("Rate with ID " + rateId + " not found.");
        }
        log.info("Rate deleted with id: {}", rateId);
        rateRepository.deleteById(rateId);
        return true;
    }
    //get rate by room id
    public List<RateDTO> getRatesByRoomId(Long roomId){
        return rateRepository.findByRoomId(roomId).stream().map(RateMapper::toDto).collect(Collectors.toList());
    }

    //for inter service communication
    public Rate getRateFor(Long roomId, LocalDate checkIn, LocalDate checkOut, int guests) {
        return rateRepository.findByRoomIdAndCheckInDateAndCheckOutDateAndNumberOfGuests(
                roomId, checkIn, checkOut, guests
        ).orElseThrow(() -> new RateNotFoundException("No rate found for this booking."));
    }

}