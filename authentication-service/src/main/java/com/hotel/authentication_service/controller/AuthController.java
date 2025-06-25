package com.hotel.authentication_service.controller;
import com.hotel.authentication_service.dto.AuthRequest;
import com.hotel.authentication_service.dto.AuthResponse;
import com.hotel.authentication_service.dto.SignupRequest;
import com.hotel.authentication_service.dto.StaffDto;

import com.hotel.authentication_service.enums.Role;
import com.hotel.authentication_service.service.AuthService;
import com.hotel.authentication_service.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    @PostMapping("/createOwner")
    public ResponseEntity<String> initOwner(@RequestBody SignupRequest request) {
        if (request.getRole() != Role.OWNER) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This route only creates OWNER role.");
        }

        // Check if any OWNER already exists
        boolean ownerExists = authService.ownerExists();
        if (ownerExists) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("An OWNER already exists.");
        }

        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @RequestBody SignupRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {

        String role = extractRoleFromToken(authHeader);
        // Extract role from the token
//        String token = authHeader.substring(7);
//        Claims claims = jwtUtil.extractAllClaims(token);
//        String creatorRole = claims.get("role", String.class);

        // Enforce rules
        if (request.getRole() == Role.OWNER && !role.equals("OWNER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only OWNER can create other OWNERS");
        }

        if (request.getRole() == Role.MANAGER && !role.equals("OWNER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only OWNER can create MANAGER");
        }

        if (request.getRole() == Role.RECEPTIONIST && !(role.equals("OWNER") || role.equals("MANAGER"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only OWNER or MANAGER can create RECEPTIONIST");
        }
        if (request.getRole() == Role.STAFF && !(role.equals("OWNER") || role.equals("MANAGER"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only OWNER or MANAGER can create STAFF");
        }
        return ResponseEntity.ok(authService.signup(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/owner-exists")
    public ResponseEntity<?> ownerExists() {
        boolean exists = authService.ownerExists();
        return ResponseEntity.ok().body(Map.of("exists", exists));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        authService.logout(token);
        return ResponseEntity.ok("Logged out successfully.");
    }


    @GetMapping("/all/{role}")
    public ResponseEntity<?> getAllStaff(@PathVariable Role role, @RequestHeader("Authorization") String authHeader) {
        String userRole = extractRoleFromToken(authHeader);

        if (!userRole.equals("OWNER")) {
            return ResponseEntity.status(403).body("Access Denied: Only OWNER can retrieve staff details.");
        }

        return ResponseEntity.ok(authService.getAllStaff(role));
    }


    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateStaff(@PathVariable String username, @RequestBody StaffDto staffDto, @RequestHeader("Authorization") String authHeader) {
        String role = extractRoleFromToken(authHeader);

        if (!role.equals("OWNER")) {
            return ResponseEntity.status(403).body("Access Denied: Only OWNER can update staff details.");
        }

        return ResponseEntity.ok(authService.updateStaff(username, staffDto));
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteStaff(@PathVariable String username, @RequestHeader("Authorization") String authHeader) {
        String role = extractRoleFromToken(authHeader);

        if (!role.equals("OWNER")) {
            return ResponseEntity.status(403).body("Only OWNER can delete staff.");
        }

        authService.deleteStaff(username);
        return ResponseEntity.ok("Staff deleted successfully.");
    }

    private String extractRoleFromToken(String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = jwtUtil.extractAllClaims(token);
        return claims.get("role", String.class);
    }
}