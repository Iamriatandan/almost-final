package com.reservation.reservationservice.fiegnclient;

import com.reservation.reservationservice.dto.clientinfodto.GuestInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "guest-service")
public interface GuestClient {
    @GetMapping("/guests/{guestId}")
    GuestInfoDto getGuestById(@PathVariable Long guestId);
}
