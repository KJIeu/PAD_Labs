package com.pad.inventoryservice.repository;

import com.pad.inventoryservice.model.Inventory;
import com.pad.inventoryservice.model.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuCode(String skuCode);

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
