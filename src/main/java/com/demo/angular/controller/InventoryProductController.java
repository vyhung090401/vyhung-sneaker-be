package com.demo.angular.controller;

import com.demo.angular.dto.InventoryProductDTO;
import com.demo.angular.service.InventoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class InventoryProductController {

    @Autowired
    InventoryProductService inventoryProductSerivce;

    @GetMapping("/inventoryProduct")
    public ResponseEntity<?> getAllProduct(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "5") int size){
        Page<InventoryProductDTO> inventoryProductDTOS = inventoryProductSerivce.getAllInventoryProduct(page,size);
        return ResponseEntity.ok(inventoryProductDTOS);
    }

    @GetMapping("/inventoryProduct/all")
    public ResponseEntity<?> getAllProductList(){
        List<InventoryProductDTO> inventoryProductDTOS = inventoryProductSerivce.getAll();
        return ResponseEntity.ok(inventoryProductDTOS);
    }

    @GetMapping("inventoryProduct/{id}")
    public ResponseEntity<?> findInventoryProduct(@PathVariable Long id){
        InventoryProductDTO inventoryProductDTO = inventoryProductSerivce.getInventoryProductById(id);
        return ResponseEntity.ok(inventoryProductDTO);
    }
}
