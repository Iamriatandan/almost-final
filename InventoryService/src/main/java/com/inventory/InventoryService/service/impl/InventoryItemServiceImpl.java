package com.inventory.InventoryService.service.impl;

import com.inventory.InventoryService.dto.ItemDTO;
import com.inventory.InventoryService.entity.InventoryItem;
import com.inventory.InventoryService.exception.InventoryItemNotFoundException;
import com.inventory.InventoryService.mapper.ItemMapper;
import com.inventory.InventoryService.repository.InventoryItemRepository;
import com.inventory.InventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class InventoryItemServiceImpl implements InventoryService {
    private InventoryItemRepository inventoryItemRepository;

    //add item in inventory
    @Override
    public ItemDTO addItem(ItemDTO itemDTO) {

        InventoryItem item = ItemMapper.toEntity(itemDTO);
        InventoryItem saved = inventoryItemRepository.save(item);
        log.info("Item saved into inventory");
    return ItemMapper.toDto(saved);
    }

    //get list of all items
    @Override
    public List<ItemDTO> getAllItems() {
        log.info("Fetching all inventory items");
        return inventoryItemRepository.findAll().stream().map(
                ItemMapper::toDto).collect(Collectors.toList());
    }

    //get ItemById
    @Override
    public ItemDTO getItemById(Long itemId) {
        log.info("Fetching item by ID: {}", itemId);
        return inventoryItemRepository.findById(itemId)
                .map(ItemMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Item not found with ID: {}", itemId);
                    return new InventoryItemNotFoundException("Item not found with ID: " + itemId);
                });
    }

    @Override
    public boolean updateItem(Long itemId, ItemDTO updatedItem) {
        log.info("Updating item with ID: {}", itemId);
        Optional<InventoryItem> optionalItem = inventoryItemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            InventoryItem existing = optionalItem.get();
            existing.setName(updatedItem.getName());
            existing.setQuantity(updatedItem.getQuantity());
            existing.setThreshold(updatedItem.getThreshold());
            existing.setDescription(updatedItem.getDescription());
            inventoryItemRepository.save(existing);
            log.info("Item updated: {}", itemId);
            return true;
        }
        log.warn("Item not found for update with ID: {}", itemId);
        throw new InventoryItemNotFoundException("Item not found with ID: " + itemId);
    }

    @Override
    public boolean deleteItem(Long id) {
        log.info("Deleting item with ID: {}", id);
        if (inventoryItemRepository.existsById(id)) {
            inventoryItemRepository.deleteById(id);
            log.info("Item deleted: {}", id);
            return true;
        }
        log.warn("Item not found for deletion with ID: {}", id);
        throw new InventoryItemNotFoundException("Item not found with ID: " + id);
    }

    @Override
    public boolean updateItemQuantity(Long id, int quantity) {
        log.info("Updating quantity for item ID: {} to {}", id, quantity);
        Optional<InventoryItem> optionalItem = inventoryItemRepository.findById(id);
        if (optionalItem.isPresent()) {
            InventoryItem item = optionalItem.get();
            item.setQuantity(quantity);
            inventoryItemRepository.save(item);
            log.info("Quantity updated for item ID: {}", id);
            return true;
        }
        log.warn("Item not found for quantity update with ID: {}", id);
        throw new InventoryItemNotFoundException("Item not found with ID: " + id);
    }

    @Override
    public List<ItemDTO> getLowStockItems(int threshold) {
        log.info("Fetching low-stock items below threshold: {}", threshold);
        return inventoryItemRepository.findByQuantityLessThan(threshold)
                .stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }
}
