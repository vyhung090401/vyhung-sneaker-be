package com.demo.angular.service;

import com.demo.angular.dto.ProductSizeDTO;
import com.demo.angular.model.ProductSize;

import java.util.List;

public interface ProductSizeService {
    List<ProductSizeDTO> getProductSizeByProductId(Long productId);
}
