package com.demo.angular.controller;


import com.demo.angular.dto.CartDTO;
import com.demo.angular.dto.ProductDTO;
import com.demo.angular.repository.CartRepository;
import com.demo.angular.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CartController {

  @Autowired
  private CartService cartService;
  @Autowired
  private CartRepository cartRepository;


  @GetMapping("/cart/{accountId}")
  public ResponseEntity<?> getCart(@PathVariable Long accountId) {
    List<CartDTO> cartDTO = cartService.getCartDetails(accountId);
    return ResponseEntity.ok().body(cartDTO);
  }

  @PostMapping("/cart/addToCart/{productSizeId}")
  public CartDTO addToCart(@PathVariable Long productSizeId) {
    CartDTO cartDTO = cartService.addToCart(productSizeId);
    return cartDTO;
  }

  @PutMapping("/cart/{cartId}")
  public CartDTO decreaseQuantityFromCart(@PathVariable Long cartId) {
    CartDTO cartDTO = cartService.decreaseQuantityProduct(cartId);
    return cartDTO;
  }

  @DeleteMapping("/cart/{cartId}")
  public void deleteProductFromCart(@PathVariable Long cartId) {
    cartRepository.deleteById(cartId);
  }


}
