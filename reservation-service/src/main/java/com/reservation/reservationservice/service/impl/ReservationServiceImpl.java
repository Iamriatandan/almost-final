package com.reservation.reservationservice.service.impl;

import com.reservation.reservationservice.dto.ReservationRequestDTO;
import com.reservation.reservationservice.dto.ReservationResponseDTO;
import com.reservation.reservationservice.dto.clientinfodto.GuestInfoDto;
import com.reservation.reservationservice.dto.clientinfodto.RoomInfoDto;
import com.reservation.reservationservice.entity.Reservation;
import com.reservation.reservationservice.entity.ReservationStatus;
import com.reservation.reservationservice.exception.GuestNotFoundException;
import com.reservation.reservationservice.exception.ReservationNotFoundException;
import com.reservation.reservationservice.exception.RoomNotFoundException;
import com.reservation.reservationservice.fiegnclient.GuestClient;
import com.reservation.reservationservice.fiegnclient.RoomClient;
import com.reservation.reservationservice.mapper.ReservationMapper;
import com.reservation.reservationservice.repository.ReservationRepository;

import java.util.UUID;
import java.util.stream.Collectors;

import com.reservation.reservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper = new ReservationMapper();

   private final GuestClient guestClient;
   private final RoomClient roomClient;
    @Override
    public List<ReservationResponseDTO> getAllReservations() {
        log.info("Fetching all reservations");
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream().map(reservation -> {
            GuestInfoDto guestInfo = guestClient.getGuestById(reservation.getGuestId());
            RoomInfoDto roomInfo = roomClient.getRoomById(reservation.getRoomId());
            return ReservationMapper.toDto(reservation, guestInfo, roomInfo);
        }).collect(Collectors.toList());
    }

    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO dto) {
        log.info("Creating reservation for guest ID: {} with room ID: {}", dto.getGuestId(), dto.getRoomId());

        GuestInfoDto guest;
        RoomInfoDto room;

        try {
            guest = guestClient.getGuestById(dto.getGuestId());
            if (guest == null) throw new GuestNotFoundException("Guest not found with ID: " + dto.getGuestId());
        } catch (Exception ex) {
            log.error("Guest service failed: {}", ex.getMessage());
            throw new GuestNotFoundException("Guest not found with ID: " + dto.getGuestId());
        }

        try {
            room = roomClient.getRoomById(dto.getRoomId());
            if (room == null) throw new RoomNotFoundException("Room not found with ID: " + dto.getRoomId());
        } catch (Exception ex) {
            log.error("Room service failed: {}", ex.getMessage());
            throw new RoomNotFoundException("Room not found with ID: " + dto.getRoomId());
        }

        Reservation reservation = ReservationMapper.toEntity(dto);
        reservation.setNumOfGuests(dto.getNumOfAdults() + dto.getNumOfChildren());
        reservation.setStatus(ReservationStatus.BOOKED);
        reservation.setReservationCode("RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        Reservation saved = reservationRepository.save(reservation);
        log.info("Reservation created with ID: {}", saved.getReservationId());

        return ReservationMapper.toDto(saved, guest, room);
    }

    @Override
    public ReservationResponseDTO getReservationById(Long reservationId) {
        log.info("Fetching reservation by ID: {}", reservationId);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation ID not found: " + reservationId));

        GuestInfoDto guestInfo = guestClient.getGuestById(reservation.getGuestId());
        RoomInfoDto roomInfo = roomClient.getRoomById(reservation.getRoomId());

        return ReservationMapper.toDto(reservation, guestInfo, roomInfo);
    }

    @Override
    public ReservationResponseDTO updateReservation(Long reservationId, ReservationRequestDTO dto) {
        log.info("Updating reservation with ID: {}", reservationId);
        Reservation existing = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation ID not found: " + reservationId));

        existing.setGuestId(dto.getGuestId());
        existing.setRoomId(dto.getRoomId());
        existing.setCheckInDate(dto.getCheckInDate());
        existing.setCheckOutDate(dto.getCheckOutDate());
        existing.setNumOfAdults(dto.getNumOfAdults());
        existing.setNumOfChildren(dto.getNumOfChildren());
        existing.setNumOfGuests(dto.getNumOfAdults() + dto.getNumOfChildren());

        Reservation updated = reservationRepository.save(existing);

        GuestInfoDto guestInfo = guestClient.getGuestById(updated.getGuestId());
        RoomInfoDto roomInfo = roomClient.getRoomById(updated.getRoomId());

        return ReservationMapper.toDto(updated, guestInfo, roomInfo);
    }

    @Override
    public boolean deleteReservation(Long reservationId) {
        log.info("Deleting reservation with ID: {}", reservationId);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation ID not found: " + reservationId));

        reservationRepository.delete(reservation);
        return true;
    }

    @Override
    public List<ReservationResponseDTO> getReservationByGuestId(Long guestId) {
        List<Reservation> reservations = reservationRepository.findByGuestId(guestId);
        if (reservations.isEmpty()) {
            throw new GuestNotFoundException("No reservations found for guest ID: " + guestId);
        }

        return reservations.stream().map(reservation -> {
            GuestInfoDto guestInfo = guestClient.getGuestById(reservation.getGuestId());
            RoomInfoDto roomInfo = roomClient.getRoomById(reservation.getRoomId());
            return ReservationMapper.toDto(reservation, guestInfo, roomInfo);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponseDTO> getReservationByRoomId(Long roomId) {
        List<Reservation> reservations = reservationRepository.findByRoomId(roomId);
        if (reservations.isEmpty()) {
            throw new RoomNotFoundException("Room not found with ID: " + roomId);
        }

        return reservations.stream().map(reservation -> {
            GuestInfoDto guestInfo = guestClient.getGuestById(reservation.getGuestId());
            RoomInfoDto roomInfo = roomClient.getRoomById(reservation.getRoomId());
            return ReservationMapper.toDto(reservation, guestInfo, roomInfo);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponseDTO> getReservationByStatus(ReservationStatus status) {
        List<Reservation> reservations = reservationRepository.findReservationByStatus(status);
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("No reservations found for status: " + status);
        }

        return reservations.stream().map(reservation -> {
            GuestInfoDto guestInfo = guestClient.getGuestById(reservation.getGuestId());
            RoomInfoDto roomInfo = roomClient.getRoomById(reservation.getRoomId());
            return ReservationMapper.toDto(reservation, guestInfo, roomInfo);
        }).collect(Collectors.toList());
    }
}
