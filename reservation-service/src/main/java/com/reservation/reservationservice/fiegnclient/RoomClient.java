package com.reservation.reservationservice.fiegnclient;

import com.reservation.reservationservice.dto.clientinfodto.RoomInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "room-service")
public interface RoomClient {
    @GetMapping("/rooms/{roomId}")
    RoomInfoDto getRoomById(@PathVariable Long roomId);
}
