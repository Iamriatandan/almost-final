package com.guest.guest_service.repository;

import com.guest.guest_service.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GuestRepository extends JpaRepository<Guest,Long> {

    //optional methods , will help in checking duplicate values
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone);
    boolean existsByMemberCode(String memberCode);
}
