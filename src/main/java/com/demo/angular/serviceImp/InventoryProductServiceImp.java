package com.demo.angular.serviceImp;

import com.demo.angular.dto.InventoryProductDTO;
import com.demo.angular.model.InventoryProduct;
import com.demo.angular.model.Product;
import com.demo.angular.repository.InventoryProductRepository;
import com.demo.angular.service.InventoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryProductServiceImp implements InventoryProductService {

    @Autowired
    InventoryProductRepository inventoryProductRepository;

    @Override
    public Page<InventoryProductDTO> getAllInventoryProduct(int page, int size){
        Page<InventoryProduct> inventoryProductPage =  inventoryProductRepository.findAll(PageRequest.of(page,size));
        List<InventoryProduct> inventoryProducts = inventoryProductPage.getContent();
        List<InventoryProductDTO> inventoryProductDTOS = new ArrayList<>();
        for(InventoryProduct inventoryProduct: inventoryProducts){
            InventoryProductDTO inventoryProductDTO = new InventoryProductDTO(inventoryProduct);
            inventoryProductDTOS.add(inventoryProductDTO);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<InventoryProductDTO> inventoryProductDTOPage = new PageImpl<>(inventoryProductDTOS,pageable,inventoryProductPage.getTotalElements());
        return inventoryProductDTOPage;
    }

    @Override
    public List<InventoryProductDTO> getAll(){
        List<InventoryProduct> inventoryProducts = inventoryProductRepository.findAll();
        List<InventoryProductDTO> inventoryProductDTOS = new ArrayList<>();
        for (InventoryProduct inventoryProduct : inventoryProducts){
            InventoryProductDTO inventoryProductDTO = new InventoryProductDTO(inventoryProduct);
            inventoryProductDTOS.add(inventoryProductDTO);
        }
        return inventoryProductDTOS;
    }

    @Override
    public InventoryProductDTO getInventoryProductById(Long id){
        InventoryProduct inventoryProduct = inventoryProductRepository.findByProductId(id);
        InventoryProductDTO inventoryProductDTO = new InventoryProductDTO(inventoryProduct);
        return inventoryProductDTO;
    }



}
