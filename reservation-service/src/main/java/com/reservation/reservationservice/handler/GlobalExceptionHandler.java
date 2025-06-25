package com.reservation.reservationservice.handler;

import com.reservation.reservationservice.entity.Reservation;
import com.reservation.reservationservice.exception.GuestNotFoundException;
import com.reservation.reservationservice.exception.InvalidNumberOfGuestException;
import com.reservation.reservationservice.exception.ReservationNotFoundException;
import com.reservation.reservationservice.exception.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //for reservation id
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String > handleReservationNotFound(ReservationNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    //for room id
    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<String > handleRoomNotFound(RoomNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    //for guest id
    @ExceptionHandler(GuestNotFoundException.class)
    public ResponseEntity<String > handleGuestNotFound(GuestNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    //invalid no of guest
    @ExceptionHandler(InvalidNumberOfGuestException.class)
    public ResponseEntity<String> handleInvalidNumberOfGuestException(InvalidNumberOfGuestException exception){
        return new ResponseEntity<>("Invalid No of guest",HttpStatus.BAD_REQUEST);
    }

    //for general exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

}
