package com.reservation.reservationservice.dto.clientinfodto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomInfoDto {
    private Long roomId;
    private String roomNumber;
    private String roomType;
    private int capacity;
    private String status;

}
