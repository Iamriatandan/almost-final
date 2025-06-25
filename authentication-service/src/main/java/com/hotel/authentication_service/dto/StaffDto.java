package com.hotel.authentication_service.dto;
import com.hotel.authentication_service.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StaffDto {
    String name;
    String username;
    Role role;
}