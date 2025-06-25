package com.rate.rate_service.repository;

import com.rate.rate_service.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate,Long> {
    List<Rate> findByRoomId(Long roomId);

   Optional<Rate> findByRoomIdAndCheckInDateAndCheckOutDateAndNumberOfGuests(Long roomId, LocalDate checkIn, LocalDate checkOut, int guests);
}
