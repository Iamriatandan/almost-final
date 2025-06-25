package com.hotel.authentication_service.service;
import com.hotel.authentication_service.dto.AuthRequest;
import com.hotel.authentication_service.dto.AuthResponse;
import com.hotel.authentication_service.dto.SignupRequest;
import com.hotel.authentication_service.dto.StaffDto;
import com.hotel.authentication_service.enums.Role;

import java.util.List;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    String signup(SignupRequest request);
    void logout(String token);
    boolean ownerExists();

    StaffDto updateStaff(String username, StaffDto staffDto);

    List<StaffDto> getAllStaff(Role role);

    void deleteStaff(String username);
}