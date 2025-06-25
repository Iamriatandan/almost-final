package com.guest.guest_service.exception;

public class DuplicatePhoneNumberException extends RuntimeException{
    public DuplicatePhoneNumberException(String message) {
        super(message);
    }
}
