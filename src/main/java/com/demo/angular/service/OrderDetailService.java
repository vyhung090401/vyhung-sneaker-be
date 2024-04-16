package com.demo.angular.service;

import com.demo.angular.dto.OrderDTO;
import com.demo.angular.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailDTO> getOrderDetail(Long orderId);

}
