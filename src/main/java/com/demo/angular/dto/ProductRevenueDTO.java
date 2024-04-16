package com.demo.angular.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductRevenueDTO {
    private String productName;
    private Double revenue;

    @Override
    public String toString() {
        return "Name:" + productName +",revenue: "+revenue;
    }
}
