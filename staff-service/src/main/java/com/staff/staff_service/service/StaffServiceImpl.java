package com.staff.staff_service.service;


import com.staff.staff_service.dto.StaffDTO;
import com.staff.staff_service.entity.Staff;
import com.staff.staff_service.enums.Occupation;
import com.staff.staff_service.exception.DuplicateSaffCodeException;
import com.staff.staff_service.exception.StaffNotFoundException;
import com.staff.staff_service.mapper.StaffMapper;
import com.staff.staff_service.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService{
    private final StaffRepository staffRepository;
    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    // Add new staff
    @Override
    public StaffDTO addStaff(StaffDTO staffDTO) {
        if (staffRepository.existsByStaffCode(staffDTO.getStaffCode())) {
            throw new DuplicateSaffCodeException("Staff with code " + staffDTO.getStaffCode() + " already exists.");
        }
        Staff staff = StaffMapper.toEntity(staffDTO);
        Staff saved = staffRepository.save(staff);
        return StaffMapper.toDto(saved);
    }

    // Get all staff
    @Override
    public List<StaffDTO> getAllStaff() {
        return staffRepository.findAll()
                .stream()
                .map(StaffMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get staff by ID
    @Override
    public StaffDTO getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException("Staff with ID " + id + " not found."));
        return StaffMapper.toDto(staff);
    }

    // Update staff
    @Override
    public StaffDTO updateStaff(Long id, StaffDTO staffDTO) {
        Staff existing = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException("Staff with ID " + id + " not found."));

        // Only checking code duplication if it's changed
        if (!existing.getStaffCode().equals(staffDTO.getStaffCode()) &&
                staffRepository.existsByStaffCode(staffDTO.getStaffCode())) {
            throw new DuplicateSaffCodeException("Staff code already exists: " + staffDTO.getStaffCode());
        }

        Staff updatedStaff = StaffMapper.toEntity(staffDTO);
        updatedStaff.setStaffId(id);
        return StaffMapper.toDto(staffRepository.save(updatedStaff));
    }

    // Delete staff
    @Override
    public boolean deleteStaff(Long id) {
        if (!staffRepository.existsById(id)) {
            throw new StaffNotFoundException("Staff with ID " + id + " not found.");
        }
        staffRepository.deleteById(id);
        return true;
    }

    // Filter by Occupation
    @Override
    public List<StaffDTO> getStaffByOccupation(String occupation) {
        Occupation occEnum;
        try {
            occEnum = Occupation.valueOf(occupation.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StaffNotFoundException("Invalid occupation type: " + occupation);
        }

        return staffRepository.findByOccupation(occEnum)
                .stream()
                .map(StaffMapper::toDto)
                .collect(Collectors.toList());
    }
}
