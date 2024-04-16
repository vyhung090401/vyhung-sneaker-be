package com.demo.angular.dto;

import com.demo.angular.model.EStatusProduct;
import com.demo.angular.model.Product;
import com.demo.angular.model.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.color = product.getColor();
        this.brand = product.getBrand();
        this.status = product.getStatus();
        this.quantity = product.getQuantity();
    }

    private Long id;
    private String name;
    private Double price;
    private String color;
    private String brand;
    private EStatusProduct status;
    private Integer quantity;
    private List<ProductSizeDTO> productSizes;
    private List<ImageDTO> images = new ArrayList<>();
}
