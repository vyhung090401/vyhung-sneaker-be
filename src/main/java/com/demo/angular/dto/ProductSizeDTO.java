package com.demo.angular.dto;

import com.demo.angular.model.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSizeDTO {
    public ProductSizeDTO(ProductSize productSize){
        this.id = productSize.getId();
//        this.productId = productSize.getProduct().getId();
        this.quantity = productSize.getQuantity();
    }
    private Long id;
    private Long productId;
    private SizeDTO sizeDTO;
    private Integer quantity;
}
