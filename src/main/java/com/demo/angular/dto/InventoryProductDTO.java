package com.demo.angular.dto;

import com.demo.angular.model.Inventory;
import com.demo.angular.model.InventoryProduct;
import com.demo.angular.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryProductDTO {

    public InventoryProductDTO(InventoryProduct inventoryProduct){
        this.id = inventoryProduct.getId();
        this.inventoryName = inventoryProduct.getInventory().getName();
        this.productName = inventoryProduct.getProduct().getName();
        this.color = inventoryProduct.getProduct().getColor();
        this.brand = inventoryProduct.getProduct().getBrand();
        this.inventoryProductQty = inventoryProduct.getQuantity();
        this.purchasePrice = inventoryProduct.getPurchasePrice();
        this.retailPrice = inventoryProduct.getProduct().getPrice();
        this.exportProductQty = inventoryProduct.getProduct().getQuantity();
        this.productId = inventoryProduct.getProduct().getId();
    }

    private Long id;
    private String inventoryName;
    private String productName;
    private String color;
    private String brand;
    private Integer inventoryProductQty;
    private Integer exportProductQty;
    private Double purchasePrice;
    private Double retailPrice;
    private Long productId;
}
