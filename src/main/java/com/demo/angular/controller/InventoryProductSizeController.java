package com.demo.angular.controller;

import com.demo.angular.dto.InventoryProductSizeDTO;
import com.demo.angular.payload.request.ExportReceiptForm;
import com.demo.angular.service.InventoryProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class InventoryProductSizeController {

    @Autowired
    InventoryProductSizeService inventoryProductSizeService;

    @GetMapping("/inventoryProductsize")
    public ResponseEntity<?> getInventoryProductSizeList(@RequestParam Long productId){
        List<InventoryProductSizeDTO> inventoryProductSizeDTOS = inventoryProductSizeService.getInventoryProductSizeList(productId);
        return ResponseEntity.ok(inventoryProductSizeDTOS);
    }

    @PostMapping("inventoryProductsize/export")
    public ResponseEntity<?> exportInventory(@RequestBody ExportReceiptForm exportReceiptForm){
        inventoryProductSizeService.exportInventoryProduct(exportReceiptForm);
        return ResponseEntity.ok("Exporting Successfully!");
    }

    @PostMapping("inventoryProductsize/receipt")
    public ResponseEntity<?> receiptInventory(@RequestBody ExportReceiptForm exportReceiptForm){
        inventoryProductSizeService.receiptInventoryProduct(exportReceiptForm);
        return ResponseEntity.ok("Importing Successfully!");
    }

}
