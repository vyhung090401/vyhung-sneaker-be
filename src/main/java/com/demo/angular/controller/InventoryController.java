package com.demo.angular.controller;

import com.demo.angular.dto.InventoryDTO;
import com.demo.angular.dto.ProductDTO;
import com.demo.angular.model.Inventory;
import com.demo.angular.repository.InventoryRepository;
import com.demo.angular.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    InventoryRepository inventoryRepository;

    @GetMapping("/inventory")
    public ResponseEntity<?> getAllInventory(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "5") int size){
        Page<InventoryDTO> inventoryDTOPage = inventoryService.getAllInventory(page,size);
        return ResponseEntity.ok(inventoryDTOPage);
    }

    @GetMapping("/inventory/list")
    public ResponseEntity<?> getInventoryList(){
        List<InventoryDTO> inventoryDTOList = inventoryService.getInventoryList();
        return ResponseEntity.ok(inventoryDTOList);
    }

    @PostMapping("/inventory")
    public ResponseEntity<?> createInventory(@RequestBody InventoryDTO inventoryDTO){
        inventoryService.createNewInventory(inventoryDTO);
        return ResponseEntity.ok(inventoryDTO);
    }


}
