package com.room.room_service.exception;

public class DuplicateRoomNumberException extends RuntimeException{
    public DuplicateRoomNumberException(String message) {
        super(message);
    }
}
