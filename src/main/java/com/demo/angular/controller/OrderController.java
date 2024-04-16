package com.demo.angular.controller;

import com.demo.angular.dto.CartDTO;
import com.demo.angular.dto.OrderDTO;
import com.demo.angular.dto.ProductRevenueDTO;
import com.demo.angular.model.EStatusOrder;
import com.demo.angular.model.Order;
import com.demo.angular.payload.request.ChangeStatusReq;
import com.demo.angular.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/order/{accountId}")
    public ResponseEntity<?> getOrderHistory(@PathVariable Long accountId){
        List<OrderDTO> orderDTOList = orderService.getOrderListByAccount(accountId);
        return ResponseEntity.ok(orderDTOList);
    }

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderRequest){
        orderService.addOrder(orderRequest);
        return ResponseEntity.ok(orderRequest);
    }

    @GetMapping("/order/findOrder/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId){
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/order")
    public ResponseEntity<?> getOrderList(@RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "5") int size){
        Page<OrderDTO> orderDTOList = orderService.getOrderList(page, size);
        return ResponseEntity.ok(orderDTOList);
    }

    @GetMapping("/order/allOrder")
    public ResponseEntity<?> getAllOrderList(){
        List<OrderDTO> orderDTOList = orderService.getAllOrderList();
        return ResponseEntity.ok(orderDTOList);
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity<?> updateStatusOrder(@PathVariable Long orderId,@RequestBody ChangeStatusReq statusOrd){
        OrderDTO orderDTO = orderService.updateStatus(orderId,statusOrd);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/order/findOrderByStatus")
    public ResponseEntity<?> getOrderListByStatus(@RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size", defaultValue = "5") int size,
                                                  @RequestParam EStatusOrder status){
        Page<OrderDTO> orderDTOList = orderService.getOrderListByStatus(page, size,status);
        return ResponseEntity.ok(orderDTOList);
    }

    @GetMapping("/order/findByDateAsc")
    public ResponseEntity<?> getOrderByDateAsc(@RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "5") int size){
        Page<OrderDTO> orderDTOS = orderService.getOrderListByDateAsc(page, size);
        return ResponseEntity.ok(orderDTOS);
    }
    @GetMapping("/order/findByDateDesc")
    public ResponseEntity<?> getOrderByDateDesc(@RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "size", defaultValue = "5") int size){
        Page<OrderDTO> orderDTOS = orderService.getOrderListByDateDesc(page, size);
        return ResponseEntity.ok(orderDTOS);
    }

    @GetMapping("/order/findByStatusDateAsc")
    public ResponseEntity<?> getOrderByStatusDateAsc(@RequestParam(name = "page", defaultValue = "0") int page,
                                                     @RequestParam(name = "size", defaultValue = "5") int size,
                                                     @RequestParam EStatusOrder status){
        Page<OrderDTO> orderDTOS = orderService.getOrderListByStatusDateAsc(page, size, status);
        return ResponseEntity.ok(orderDTOS);
    }

    @GetMapping("/order/findByStatusDateDesc")
    public ResponseEntity<?> getOrderByStatusDateDesc(@RequestParam(name = "page", defaultValue = "0") int page,
                                                      @RequestParam(name = "size", defaultValue = "5") int size,
                                                      @RequestParam EStatusOrder status){
        Page<OrderDTO> orderDTOS = orderService.getOrderListByStatusDateDesc(page, size, status);
        return ResponseEntity.ok(orderDTOS);
    }

    @GetMapping("/order/findById/{orderId}")
    public ResponseEntity<?> getOrderByOrderId(@RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "5") int size,
                                               @PathVariable Long orderId){
        Page<OrderDTO> orderDTOS = orderService.getOrderListById(page,size,orderId);
        return ResponseEntity.ok(orderDTOS);
    }

    @GetMapping("/order/saleOfMonth")
    public ResponseEntity<?> getRevenueByMonth(@RequestParam int month,
                                               @RequestParam int year){
        List<Double> results = orderService.getRevenueDaily(month,year);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/order/brandSaleOfMonth")
    public ResponseEntity<?> getBrandRevenueByMonth(@RequestParam int month,
                                                    @RequestParam int year){
        List<Object[]> results = orderService.getBrandRevenueMonthly(month,year);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/order/productSaleOfMonth")
    public ResponseEntity<?> getBrandRevenueByMonth(@RequestParam int month,
                                                    @RequestParam int year,
                                                    @RequestParam String brand){
        List<ProductRevenueDTO> results = orderService.getProductRevenueMonthly(brand,month,year);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/order/statisticStatusOrder")
    public ResponseEntity<?> getStatisticStatusOrder(@RequestParam int month,
                                                     @RequestParam int year){
        List<Object[]> results = orderService.statisticStatusOrder(month,year);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/order/top10AccountConsume")
    public ResponseEntity<?> getTop10AccountConsume(@RequestParam int month,
                                                    @RequestParam int year){
        List<Object[]> results = orderService.getTop10AccountConsume(month,year);
        return ResponseEntity.ok(results);
    }
}
