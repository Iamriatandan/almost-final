package com.reservation.reservationservice.exception;

public class InvalidNumberOfGuestException extends RuntimeException{
    public InvalidNumberOfGuestException(String message) {
        super(message);
    }
}
