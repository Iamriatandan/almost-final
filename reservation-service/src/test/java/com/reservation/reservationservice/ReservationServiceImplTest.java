package com.reservation.reservationservice.service.impl;

import com.reservation.reservationservice.dto.ReservationRequestDTO;
import com.reservation.reservationservice.dto.clientinfodto.GuestInfoDto;
import com.reservation.reservationservice.dto.clientinfodto.RoomInfoDto;
import com.reservation.reservationservice.entity.Reservation;
import com.reservation.reservationservice.entity.ReservationStatus;
import com.reservation.reservationservice.exception.ReservationNotFoundException;
import com.reservation.reservationservice.fiegnclient.GuestClient;
import com.reservation.reservationservice.fiegnclient.RoomClient;
import com.reservation.reservationservice.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private GuestClient guestClient;

    @Mock
    private RoomClient roomClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReservationById_success() {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setReservationId(1L);
        reservation.setGuestId(101L);
        reservation.setRoomId(202L);
        reservation.setCheckInDate(LocalDate.now());
        reservation.setCheckOutDate(LocalDate.now().plusDays(3));
        reservation.setStatus(ReservationStatus.BOOKED);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(guestClient.getGuestById(101L)).thenReturn(new GuestInfoDto());
        when(roomClient.getRoomById(202L)).thenReturn(new RoomInfoDto());

        // Act
        var response = reservationService.getReservationById(1L);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getReservationId()).isEqualTo(1L);
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetReservationById_notFound() {
        // Arrange
        when(reservationRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ReservationNotFoundException.class, () -> {
            reservationService.getReservationById(2L);
        });

        verify(reservationRepository, times(1)).findById(2L);
    }

    @Test
    void testCreateReservation_success() {
        // Arrange
        ReservationRequestDTO requestDTO = new ReservationRequestDTO();
        requestDTO.setGuestId(101L);
        requestDTO.setRoomId(202L);
        requestDTO.setCheckInDate(LocalDate.now());
        requestDTO.setCheckOutDate(LocalDate.now().plusDays(2));
        requestDTO.setNumOfAdults(2);
        requestDTO.setNumOfChildren(1);

        when(guestClient.getGuestById(101L)).thenReturn(new GuestInfoDto());
        when(roomClient.getRoomById(202L)).thenReturn(new RoomInfoDto());
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> {
            Reservation r = i.getArgument(0);
            r.setReservationId(1L);
            return r;
        });

        // Act
        var response = reservationService.createReservation(requestDTO);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getReservationId()).isEqualTo(1L);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }
}
