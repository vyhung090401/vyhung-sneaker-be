package com.demo.angular.service;

import com.demo.angular.dto.CartDTO;
import com.demo.angular.dto.ProductDTO;
import com.demo.angular.model.Cart;


import java.util.List;

public interface CartService {
    CartDTO addToCart(Long productSizeId);
    List<CartDTO> getCartDetails(Long accountId);

    CartDTO decreaseQuantityProduct(Long cartId);



}
