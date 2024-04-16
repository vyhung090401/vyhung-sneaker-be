package com.demo.angular.dto;

import com.demo.angular.model.Order;
import com.demo.angular.model.OrderDetail;
import com.demo.angular.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    public OrderDetailDTO(OrderDetail orderDetail){
        this.quantity = orderDetail.getQuantity();
        this.totalPrice = orderDetail.getTotalPrice();
        this.price = orderDetail.getPrice();
        this.picByte = orderDetail.getProductSize().getProduct().getImages().get(0).getPicByte();
        this.quantityProduct = orderDetail.getProductSize().getQuantity();
    }

    private ProductSizeDTO productSize;

    private byte[] picByte;

    private ProductDTO product;

    private Double price;

    private Integer quantity;

    private Integer quantityProduct;

    private Double totalPrice;
}
