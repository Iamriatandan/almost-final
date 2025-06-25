package com.inventory.InventoryService.controller;
import com.inventory.InventoryService.dto.ItemDTO;
import com.inventory.InventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class InventoryItemController {
    private final InventoryService inventoryService;

    //add new item
    @PostMapping
    public ResponseEntity<ItemDTO> addItem(@RequestBody ItemDTO itemDTO) {
        log.info("Received request to add inventory item");
        ItemDTO savedItem = inventoryService.addItem(itemDTO);
        return ResponseEntity.ok(savedItem);
    }

    //Get all items
    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        log.info("Received request to get all inventory items");
        return ResponseEntity.ok(inventoryService.getAllItems());
    }

    // Get item by ID
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long itemId) {
        log.info("Received request to get item with ID {}", itemId);
        return ResponseEntity.ok(inventoryService.getItemById(itemId));
    }

    // Update item
    @PutMapping("/{itemId}")
    public ResponseEntity<String> updateItem(@PathVariable Long itemId, @RequestBody ItemDTO updatedItem) {
        log.info("Received request to update item with ID {}", itemId);
        inventoryService.updateItem(itemId, updatedItem);
        return ResponseEntity.ok("Item updated successfully");
    }

    // Delete item
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        log.info("Received request to delete item with ID {}", itemId);
        inventoryService.deleteItem(itemId);
        return ResponseEntity.ok("Item deleted successfully");
    }

    // Update quantity
    @PatchMapping("/{itemId}/quantity")
    public ResponseEntity<String> updateItemQuantity(@PathVariable Long itemId, @RequestParam int quantity) {
        log.info("Received request to update quantity for item with ID {}", itemId);
        inventoryService.updateItemQuantity(itemId, quantity);
        return ResponseEntity.ok("Item quantity updated successfully");
    }

    // Get low-stock items
    @GetMapping("/low-stock")
    public ResponseEntity<List<ItemDTO>> getLowStockItems(@RequestParam int threshold) {
        log.info("Received request to get low-stock items below threshold {}", threshold);
        return ResponseEntity.ok(inventoryService.getLowStockItems(threshold));
    }
}
