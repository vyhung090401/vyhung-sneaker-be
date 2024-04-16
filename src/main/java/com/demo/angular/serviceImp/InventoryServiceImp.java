package com.demo.angular.serviceImp;

import com.demo.angular.dto.InventoryDTO;
import com.demo.angular.dto.InventoryProductDTO;
import com.demo.angular.model.Inventory;
import com.demo.angular.model.InventoryProduct;
import com.demo.angular.repository.InventoryProductRepository;
import com.demo.angular.repository.InventoryRepository;
import com.demo.angular.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceImp implements InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Override
    public Page<InventoryDTO> getAllInventory(int page, int size){
        Page<Inventory> inventoryPage =  inventoryRepository.findAll(PageRequest.of(page,size));
        List<Inventory> inventoryList = inventoryPage.getContent();
        List<InventoryDTO> inventoryDTOList = new ArrayList<>();
        for(Inventory inventory: inventoryList){
            InventoryDTO inventoryDTO = new InventoryDTO(inventory);
            inventoryDTOList.add(inventoryDTO);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<InventoryDTO> inventoryDTOPage = new PageImpl<>(inventoryDTOList,pageable,inventoryPage.getTotalElements());
        return inventoryDTOPage;
    }

    @Override
    public List<InventoryDTO> getInventoryList(){
        List<Inventory> inventoryList = inventoryRepository.findAll();
        List<InventoryDTO> inventoryDTOList = new ArrayList<>();
        for(Inventory inventory: inventoryList){
            InventoryDTO inventoryDTO = new InventoryDTO(inventory);
            inventoryDTOList.add(inventoryDTO);
        }
        return inventoryDTOList;
    }

    @Override
    public void createNewInventory(InventoryDTO inventoryDTO){
        Inventory inventory = new Inventory();
        inventory.setAddress(inventoryDTO.getAddress());
        inventory.setName(inventoryDTO.getName());
        inventoryRepository.save(inventory);
    }


}
