package com.inventory.InventoryService.handler;

import com.inventory.InventoryService.exception.InventoryItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InventoryItemNotFoundException.class)
    public ResponseEntity<String> handleItemNotFoundException(InventoryItemNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
