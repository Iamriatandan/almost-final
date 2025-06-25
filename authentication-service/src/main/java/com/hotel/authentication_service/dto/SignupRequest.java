package com.hotel.authentication_service.dto;
import com.hotel.authentication_service.enums.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String name;
    private String username;
    private String password;
    private Role role;
}