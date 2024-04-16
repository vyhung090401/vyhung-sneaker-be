package com.demo.angular.service;

import com.demo.angular.dto.InventoryDTO;
import com.demo.angular.dto.InventoryProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {

    Page<InventoryDTO> getAllInventory(int page, int size);
    List<InventoryDTO> getInventoryList();

    void createNewInventory(InventoryDTO inventoryDTO);
}
