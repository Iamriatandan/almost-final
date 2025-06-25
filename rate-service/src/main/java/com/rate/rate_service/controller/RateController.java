package com.rate.rate_service.controller;

import com.rate.rate_service.dto.RateDTO;
import com.rate.rate_service.entity.Rate;
import com.rate.rate_service.mapper.RateMapper;
import com.rate.rate_service.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
@Slf4j
public class RateController {

    private final RateService rateService;

    // Create a new rate
    @PostMapping
    public ResponseEntity<RateDTO> createRate(@RequestBody RateDTO rateDTO) {
        log.info("API Call: Create Rate");
        RateDTO created = rateService.createRate(rateDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get all rates
    @GetMapping
    public ResponseEntity<List<RateDTO>> getAllRates() {
        log.info("API Call: Get All Rates");
        List<RateDTO> rates = rateService.getAllRates();
        return new ResponseEntity<>(rates, HttpStatus.OK);
    }

    // Get rate by ID
    @GetMapping("/{rateId}")
    public ResponseEntity<RateDTO> getRateById(@PathVariable Long rateId) {
        log.info("API Call: Get Rate by ID {}", rateId);
        RateDTO rate = rateService.getRateById(rateId);
        return new ResponseEntity<>(rate, HttpStatus.OK);
    }

    // Update rate by ID
    @PutMapping("/{rateId}")
    public ResponseEntity<RateDTO> updateRate(@PathVariable Long rateId, @RequestBody RateDTO rateDTO) {
        log.info("API Call: Update Rate with ID {}", rateId);
        RateDTO updated = rateService.updateRate(rateId, rateDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Delete rate by ID
    @DeleteMapping("/{rateId}")
    public ResponseEntity<String> deleteRate(@PathVariable Long rateId) {
        log.info("API Call: Delete Rate with ID {}", rateId);
        rateService.deleteRate(rateId);
        return new ResponseEntity<>("Rate with ID " + rateId + " deleted successfully.", HttpStatus.OK);
    }

    //get rates by roomId
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<RateDTO>> getRatesByRoomId(@PathVariable Long roomId){
        log.info("API Call: Get Rates for Room ID {}", roomId);
        List<RateDTO> rates = rateService.getRatesByRoomId(roomId);
        return new ResponseEntity<>(rates,HttpStatus.OK);
    }

    //get rate for inter-communication
    @GetMapping("/get-rate")
    public ResponseEntity<RateDTO> getRateForBooking(
            @RequestParam Long roomId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam int numberOfGuests
            ){
        Rate rate = rateService.getRateFor(roomId,checkInDate,checkOutDate,numberOfGuests);
        RateDTO dto = RateMapper.toDto(rate);
        return ResponseEntity.ok(dto);
    }
}
