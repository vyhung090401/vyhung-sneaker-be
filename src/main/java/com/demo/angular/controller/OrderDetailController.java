package com.demo.angular.controller;

import com.demo.angular.dto.OrderDTO;
import com.demo.angular.dto.OrderDetailDTO;
import com.demo.angular.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class OrderDetailController {

    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/orderDetail/{orderId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long orderId){
        List<OrderDetailDTO> orderDetailDTOList = orderDetailService.getOrderDetail(orderId);
        return ResponseEntity.ok(orderDetailDTOList);
    }

}
