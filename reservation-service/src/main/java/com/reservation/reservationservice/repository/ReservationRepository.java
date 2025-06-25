package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.entity.Reservation;
import com.reservation.reservationservice.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    //custom interface methods that allows services to intercommunicate
    List<Reservation> findByRoomId(Long roomId);
    List<Reservation> findReservationByStatus(ReservationStatus status);
    List<Reservation> findByGuestId(Long guestId);

}
