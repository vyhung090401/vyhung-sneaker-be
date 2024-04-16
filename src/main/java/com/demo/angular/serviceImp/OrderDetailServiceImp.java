package com.demo.angular.serviceImp;

import com.demo.angular.dto.*;
import com.demo.angular.model.*;
import com.demo.angular.repository.OrderDetailRepository;
import com.demo.angular.repository.ProductRepository;
import com.demo.angular.repository.ProductSizeRepository;
import com.demo.angular.service.OrderDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderDetailServiceImp implements OrderDetailService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;

    public List<OrderDetailDTO> getOrderDetail(Long orderId){
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderId(orderId);
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        for(OrderDetail orderDetail: orderDetailList){
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO(orderDetail);
            ProductDTO productDTO = new ProductDTO(productRepository.findById(orderDetail.getProductSize().getProduct().getId()).get());
            ProductSize productSize = productSizeRepository.findById(orderDetail.getProductSize().getId()).get();
            Size size = productSize.getSize();
            SizeDTO sizeDTO = new SizeDTO(size);
            ProductSizeDTO productSizeDTO = new ProductSizeDTO(productSize);
            productSizeDTO.setSizeDTO(sizeDTO);
            orderDetailDTO.setProduct(productDTO);
            orderDetailDTO.setProductSize(productSizeDTO);
            orderDetailDTOList.add(orderDetailDTO);
        }
        return orderDetailDTOList;
    }

}
