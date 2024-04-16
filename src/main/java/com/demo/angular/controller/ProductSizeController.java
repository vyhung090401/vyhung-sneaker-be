package com.demo.angular.controller;

import com.demo.angular.dto.ProductDTO;
import com.demo.angular.dto.ProductSizeDTO;
import com.demo.angular.model.ProductSize;
import com.demo.angular.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class ProductSizeController {
  @Autowired
  ProductSizeService productSizeService;

  @GetMapping("/productsize/{productId}")
  public ResponseEntity<?> getSizeByProduct(@PathVariable Long productId) {
    List<ProductSizeDTO> productSizeDTOS = productSizeService.getProductSizeByProductId(productId);
    return ResponseEntity.ok(productSizeDTOS);
  }
}
