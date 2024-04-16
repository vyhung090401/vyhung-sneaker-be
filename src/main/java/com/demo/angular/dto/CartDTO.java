package com.demo.angular.dto;

import com.demo.angular.model.Cart;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
    public CartDTO(Cart cart){
        this.id = cart.getId();
        this.accountId = cart.getAccount().getId();
        this.quantity = cart.getQuantity();
    }

    Long id;

    Long accountId;

    ProductSizeDTO productSize;

    Integer quantity;

    ProductDTO product;

    Double totalPrice;
}
