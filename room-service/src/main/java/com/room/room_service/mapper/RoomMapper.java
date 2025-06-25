package com.room.room_service.mapper;

import com.room.room_service.dto.RoomDTO;
import com.room.room_service.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    //from entity to dto
    public static RoomDTO toRoomDTO(Room room){
        if(room == null) return null;
        return new RoomDTO(room.getRoomId(),
                room.getRoomNumber(),
                room.getType(),
                room.getDescription(),
                room.getPricePerNight(),
                room.getStatus());
    }

    //convert dto to entity
    public static Room toEntity(RoomDTO roomDTO) {
        if (roomDTO == null) return null;
        return Room.builder()
                .roomId(roomDTO.getRoomId())
                .roomNumber(roomDTO.getRoomNumber())
                .type(roomDTO.getType())
                .description(roomDTO.getDescription())
                .pricePerNight(roomDTO.getPricePerNight())
                .status(roomDTO.getStatus())
                .build();

    }
}
