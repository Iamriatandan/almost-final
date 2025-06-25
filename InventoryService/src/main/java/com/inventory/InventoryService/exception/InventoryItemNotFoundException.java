package com.inventory.InventoryService.exception;

public class InventoryItemNotFoundException extends  RuntimeException{
    public InventoryItemNotFoundException (String message){
        super(message);
    }
}
