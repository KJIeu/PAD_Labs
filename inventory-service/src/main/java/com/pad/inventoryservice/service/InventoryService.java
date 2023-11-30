package com.pad.inventoryservice.service;


import com.pad.inventoryservice.dto.InventoryResponse;
import com.pad.inventoryservice.model.OrderLineItems;
import com.pad.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }

//    @Transactional(readOnly = true)
//    public List<InventoryResponse> isInStock(List<OrderLineItems> orderLineItemsList) {
//        System.out.println("hbhbhh");
//        return inventoryRepository.findBySkuCodeIn(orderLineItemsList).stream()
//                .map(inventory -> {
//                    OrderLineItems orderLineItem = getOrderLineItemBySkuCode(orderLineItemsList, inventory.getSkuCode());
//                    boolean isAvailable = inventory.getQuantity() >= orderLineItem.getQuantity();
//
//                    return InventoryResponse.builder()
//                            .skuCode(inventory.getSkuCode())
//                            .isInStock(isAvailable)
//                            .build();
//                })
//                .toList();
//    }

    private OrderLineItems getOrderLineItemBySkuCode(List<OrderLineItems> orderLineItemsList, String skuCode) {
        return orderLineItemsList.stream()
                .filter(orderLineItem -> orderLineItem.getSkuCode().equals(skuCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("OrderLineItem not found for SKU code: " + skuCode));
    }

}
