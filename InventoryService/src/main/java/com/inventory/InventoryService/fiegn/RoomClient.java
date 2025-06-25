package com.inventory.InventoryService.fiegn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "room-service")
public interface RoomClient {
}
