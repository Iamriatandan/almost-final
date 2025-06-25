package com.inventory.InventoryService.mapper;

import com.inventory.InventoryService.dto.ItemDTO;
import com.inventory.InventoryService.entity.InventoryItem;

public class ItemMapper {
    //from dto to entity
    public static InventoryItem toEntity(ItemDTO itemDTO){
        if(itemDTO == null) return null;
        return InventoryItem.builder()
                .itemId( itemDTO.getItemId())
                .name(itemDTO.getName())
                .quantity(itemDTO.getQuantity())
                .threshold(itemDTO.getThreshold())
                .description(itemDTO.getDescription())
                .build();
    }
    //from entity to dto
    public static ItemDTO toDto(InventoryItem item){
        if(item == null) return null;
        return ItemDTO.builder()
                .itemId(item.getItemId())
                .name(item.getName())
                .quantity(item.getQuantity())
                .threshold(item.getThreshold())
                .description(item.getDescription()).build();

    }
}
