package com.staff.staff_service.mapper;

import com.staff.staff_service.dto.StaffDTO;
import com.staff.staff_service.entity.Staff;

public class StaffMapper {
    //from entity to dto
    public static StaffDTO toDto(Staff staff){
        if(staff == null) return null;
        return new StaffDTO( staff.getStaffId(),
                staff.getStaffCode(),
                staff.getName(),
                staff.getAddress(),
                staff.getEmail(),
                staff.getNic(),
                staff.getAge(),
                staff.getOccupation(),
                staff.getSalary());
    }

    //from dto to entity
    public static Staff toEntity (StaffDTO dto){
        if(dto == null) return null;
        return new Staff( dto.getStaffId(),
                dto.getStaffCode(),
                dto.getName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getNic(),
                dto.getAge(),
                dto.getOccupation(),
                dto.getSalary());
    }
}
