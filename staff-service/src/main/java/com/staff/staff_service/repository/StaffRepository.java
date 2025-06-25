package com.staff.staff_service.repository;

import com.staff.staff_service.entity.Staff;
import com.staff.staff_service.enums.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {
    boolean existsByStaffCode(String staffCode);
    List<Staff> findByOccupation(Occupation occupation);
}
