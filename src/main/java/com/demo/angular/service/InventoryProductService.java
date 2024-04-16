package com.demo.angular.service;

import com.demo.angular.dto.InventoryProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryProductService {

    Page<InventoryProductDTO> getAllInventoryProduct(int page, int size);

    List<InventoryProductDTO> getAll();

    InventoryProductDTO getInventoryProductById(Long id);

}
