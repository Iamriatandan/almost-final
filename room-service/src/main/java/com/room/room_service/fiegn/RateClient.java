package com.room.room_service.fiegn;

import com.room.room_service.dto.RateResponseDto;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "rate-service")
public interface RateClient {
    @GetMapping("/rates/get-rate")
    RateResponseDto getRate(
            @RequestParam Long roomId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam int numberOfGuests
    );
}
