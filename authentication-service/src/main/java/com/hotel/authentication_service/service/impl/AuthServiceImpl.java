package com.hotel.authentication_service.service.impl;

import com.hotel.authentication_service.dto.AuthRequest;
import com.hotel.authentication_service.dto.AuthResponse;
import com.hotel.authentication_service.dto.SignupRequest;
import com.hotel.authentication_service.dto.StaffDto;
import com.hotel.authentication_service.entity.Staff;
import com.hotel.authentication_service.enums.Role;
import com.hotel.authentication_service.repository.StaffRepository;
import com.hotel.authentication_service.service.AuthService;
import com.hotel.authentication_service.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Staff user = staffRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

        return new AuthResponse(token);
    }

    @Override
    public boolean ownerExists() {
        return staffRepository.findAll().stream()
                .anyMatch(user -> user.getRole() == Role.OWNER);
    }


    @Override
    public List<StaffDto> getAllStaff(Role role) {
        return staffRepository.findByRole(role).stream()
                .map(staff -> StaffDto.builder()
                        .username(staff.getUsername())
                        .name(staff.getName())
                        .role(staff.getRole())
                        .build())
                .toList();
    }

    @Override
    public void deleteStaff(String username) {
        Staff staff = staffRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found with username: " + username));

        staffRepository.delete(staff);
    }

    @Override
    public StaffDto updateStaff(String username, StaffDto staffDto) {
        Staff staff = staffRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Staff Not Found with Username " + username));


//        staff.setName(staffDto.getName());
        staff.setRole(staffDto.getRole());

        staffRepository.save(staff);
        return StaffDto.builder()
                .username(staff.getUsername())
                .name(staff.getName())
                .role(staff.getRole())
                .build();
    }

    @Override
    public String signup(SignupRequest request) {
        Staff user = Staff.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.RECEPTIONIST)
                .build();
        staffRepository.save(user);
        return "User registered successfully!";
    }
    @Override
    public void logout(String token) {

    }
}