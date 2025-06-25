package com.inventory.InventoryService.service;

import com.inventory.InventoryService.dto.ItemDTO;
import com.inventory.InventoryService.entity.InventoryItem;

import java.util.List;

public interface InventoryService {
    ItemDTO addItem(ItemDTO itemDTO);
    List<ItemDTO> getAllItems();
    ItemDTO getItemById(Long itemId);

    boolean updateItem(Long itemId, ItemDTO updatedItem);

    boolean deleteItem(Long itemId);

    boolean updateItemQuantity(Long itemId, int quantity);

    List<ItemDTO> getLowStockItems(int threshold);

}
