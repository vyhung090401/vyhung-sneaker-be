package com.demo.angular.dto;

import com.demo.angular.model.InventoryProductSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryProductSizeDTO {

    public InventoryProductSizeDTO(InventoryProductSize inventoryProductSize){
        this.id = inventoryProductSize.getId();
        this.inventory = new InventoryDTO(inventoryProductSize.getInventory());
        this.productSize = new ProductSizeDTO(inventoryProductSize.getProductSize());
        this.sizeNumber = inventoryProductSize.getProductSize().getSize().getSizeNumber();
        this.quantity = inventoryProductSize.getQuantity();
        this.productId = inventoryProductSize.getProduct().getId();
    }
    private Long id;
    private InventoryDTO inventory;
    private ProductSizeDTO productSize;
    private Integer sizeNumber;
    private Integer quantity;
    private Long productId;
}
