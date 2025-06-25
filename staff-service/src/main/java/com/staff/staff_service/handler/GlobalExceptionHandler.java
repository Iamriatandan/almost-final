package com.staff.staff_service.handler;

import com.staff.staff_service.exception.DuplicateSaffCodeException;
import com.staff.staff_service.exception.StaffNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StaffNotFoundException.class)
    public ResponseEntity<String> handleStaffNotFound(StaffNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateSaffCodeException.class)
    public ResponseEntity<String> handleDuplicateCode(DuplicateSaffCodeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
