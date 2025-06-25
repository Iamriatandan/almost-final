package com.staff.staff_service.service;

import com.staff.staff_service.dto.StaffDTO;

import java.util.List;

public interface StaffService {
    StaffDTO addStaff(StaffDTO staffDTO);
    List<StaffDTO> getAllStaff();
    StaffDTO getStaffById(Long id);
    StaffDTO updateStaff(Long id, StaffDTO staffDTO);
    boolean deleteStaff(Long id);
    List<StaffDTO> getStaffByOccupation(String occupation);

}
