package com.demo.angular.serviceImp;

import com.demo.angular.config.jwt.JwtUtils;
import com.demo.angular.config.service.UserDetailsImp;
import com.demo.angular.dto.*;
import com.demo.angular.exception.BadRequestException;
import com.demo.angular.model.*;
import com.demo.angular.repository.AccountRepository;
import com.demo.angular.repository.CartRepository;
import com.demo.angular.repository.ProductRepository;
import com.demo.angular.repository.ProductSizeRepository;
import com.demo.angular.service.CartService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CartServiceImp implements CartService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductSizeRepository productSizeRepository;

    @Override
    public CartDTO addToCart(Long productSizeId){
        UserDetails userDetails =
            (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProductSize productSize = productSizeRepository.findById(productSizeId).get();
        Account account = accountRepository.findByUsername(userDetails.getUsername());
        Cart cartFind = cartRepository.findByAccountIdAndProductSizeId(account.getId(),productSize.getId());

        if(Objects.nonNull(cartFind)){
            if(cartFind.getQuantity() >= productSize.getQuantity()){
                throw new BadRequestException("Out of stock!");
            }
            cartFind.setQuantity(cartFind.getQuantity()+1);
            cartRepository.save(cartFind);
            CartDTO cartDTO = new CartDTO(cartFind);
            return cartDTO;
        }
        Cart cart = new Cart(productSize,account);
        cart.setQuantity(1);
        CartDTO cartDTO = new CartDTO(cart);
        cartRepository.save(cart);

        return cartDTO;
    }



    @Override
    public List<CartDTO> getCartDetails(Long accountId){
        List<Cart> cartList = cartRepository.findCartById(accountId);
        List<CartDTO> cartDTOList = new ArrayList<>();
        for(Cart cart : cartList){
            CartDTO cartDTO = new CartDTO(cart);
            ProductSize productSize = cart.getProductSize();
            Product product = productRepository.findById(productSize.getProduct().getId()).get();
            ProductDTO productDTO = new ProductDTO(product);
            for (Image img: product.getImages()) {
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setId(img.getId());
                imageDTO.setType(img.getType());
                imageDTO.setLink(img.getLink());
                productDTO.getImages().add(imageDTO);
            }
            ProductSizeDTO productSizeDTO = new ProductSizeDTO(productSize);
            SizeDTO sizeDTO = new SizeDTO(productSize.getSize());
            productSizeDTO.setSizeDTO(sizeDTO);
            cartDTO.setProductSize(productSizeDTO);
            cartDTO.setProduct(productDTO);
            cartDTO.setTotalPrice(cartDTO.getQuantity() * productDTO.getPrice());
            cartDTOList.add(cartDTO);
        }
        return cartDTOList;
    }

    @Override
    public CartDTO decreaseQuantityProduct(Long cartId){
        Cart cart = cartRepository.findById(cartId).get();
        if(cart.getQuantity()==1){
            cartRepository.deleteById(cartId);
            return null;
        }else {
            cart.setQuantity(cart.getQuantity()-1);
            cartRepository.save(cart);
            CartDTO cartDTO = new CartDTO(cart);
            return cartDTO;
        }

    }


}

