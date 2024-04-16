package com.demo.angular.service;

import com.demo.angular.dto.OrderDTO;
import com.demo.angular.dto.ProductRevenueDTO;
import com.demo.angular.model.EStatusOrder;
import com.demo.angular.payload.request.ChangeStatusReq;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    OrderDTO addOrder(OrderDTO orderRequest);
    List<OrderDTO> getOrderListByAccount(Long accountId);
    OrderDTO getOrderById(Long orderId);
    Page<OrderDTO> getOrderList(int page, int size);
    OrderDTO updateStatus(Long orderId, ChangeStatusReq status);

    Page<OrderDTO> getOrderListByStatus(int page, int size, EStatusOrder status);

    Page<OrderDTO> getOrderListByDateAsc(int page, int size);

    Page<OrderDTO> getOrderListByDateDesc(int page, int size);

    Page<OrderDTO> getOrderListByStatusDateAsc(int page, int size, EStatusOrder status);

    Page<OrderDTO> getOrderListByStatusDateDesc(int page, int size, EStatusOrder status);

    Page<OrderDTO> getOrderListById(int page,int size,Long id);

    List<Double> getRevenueDaily(int month, int year);

    List<Object[]> getBrandRevenueMonthly(int month, int year);

    List<ProductRevenueDTO> getProductRevenueMonthly(String brand, int month, int year);

    List<OrderDTO> getAllOrderList();

    List<Object[]> statisticStatusOrder(int month, int year);

    List<Object[]> getTop10AccountConsume(int month, int year);

}
