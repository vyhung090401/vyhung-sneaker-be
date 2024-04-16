package com.demo.angular.dto;

import com.demo.angular.model.Inventory;
import com.demo.angular.model.InventoryProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryDTO {

    public InventoryDTO(Inventory inventory){
        this.id = inventory.getId();
        this.name = inventory.getName();
        this.address = inventory.getAddress();
    }

    private Long id;
    private String name;
    private String address;
}
