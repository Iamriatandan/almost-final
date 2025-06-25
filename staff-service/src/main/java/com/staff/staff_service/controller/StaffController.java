package com.staff.staff_service.controller;

import com.staff.staff_service.dto.StaffDTO;
import com.staff.staff_service.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {
    private final StaffService staffService;
    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    // Add staff member
    @PostMapping
    public ResponseEntity<StaffDTO> addStaff(@RequestBody StaffDTO staffDTO) {
        StaffDTO added = staffService.addStaff(staffDTO);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    // Get all staff
    @GetMapping
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        List<StaffDTO> allStaff = staffService.getAllStaff();
        return new ResponseEntity<>(allStaff, HttpStatus.OK);
    }

    // Get staff by ID
    @GetMapping("/{id}")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable Long id) {
        StaffDTO staff = staffService.getStaffById(id);
        return new ResponseEntity<>(staff, HttpStatus.OK);
    }

    // Update staff by ID
    @PutMapping("/{id}")
    public ResponseEntity<StaffDTO> updateStaff(@PathVariable Long id, @RequestBody StaffDTO staffDTO) {
        StaffDTO updated = staffService.updateStaff(id, staffDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Delete staff by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return new ResponseEntity<>("Staff with ID " + id + " deleted successfully.", HttpStatus.OK);
    }

    // Filter staff by occupation
    @GetMapping("/occupation/{occupation}")
    public ResponseEntity<List<StaffDTO>> getStaffByOccupation(@PathVariable String occupation) {
        List<StaffDTO> filtered = staffService.getStaffByOccupation(occupation);
        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }
}
