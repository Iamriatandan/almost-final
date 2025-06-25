package com.room.room_service.dto;

import com.room.room_service.entity.RoomStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Long roomId;
    private String roomNumber;
    private String type;
    private String description;
    private double pricePerNight;
    private RoomStatus status;
}
