package com.inventory.InventoryService.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {
    private Long itemId;
    private String name;
    private Integer quantity;
    private Integer threshold;
    private String description;
}
