package com.inventory.InventoryService.repository;

import com.inventory.InventoryService.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem,Long> {

    // Custom method to find low-stock items
    List<InventoryItem> findByQuantityLessThan(int threshold);
}
