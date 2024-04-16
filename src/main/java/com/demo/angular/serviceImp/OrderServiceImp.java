package com.demo.angular.serviceImp;

import com.demo.angular.dto.*;
import com.demo.angular.exception.BadRequestException;
import com.demo.angular.model.*;
import com.demo.angular.payload.request.ChangeStatusReq;
import com.demo.angular.repository.*;
import com.demo.angular.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImp implements OrderService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ProductSizeRepository productSizeRepository;

    @Override
    public OrderDTO addOrder(OrderDTO orderRequest){
        Order order = new Order();
        order.setName(orderRequest.getName());
        order.setEmail(orderRequest.getEmail());
        order.setAddress(orderRequest.getAddress());
        order.setOrderTotal(orderRequest.getOrderTotal());
        order.setAccount(accountRepository.findById(orderRequest.getAccountId()).get());
        order.setPhoneNumber(orderRequest.getPhoneNumber());
        order.setStatus(EStatusOrder.PENDING);
        order.setCreateDate(new Date());
        orderRepository.save(order);
        List<OrderDetailDTO> orderDetailDTOList = orderRequest.getOrderDetailList();
        for(OrderDetailDTO orderDetailDTO : orderDetailDTOList){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPrice(orderDetailDTO.getTotalPrice()/orderDetailDTO.getQuantity());
            orderDetail.setTotalPrice(orderDetailDTO.getTotalPrice());
            ProductSize productSize = productSizeRepository.findById(orderDetailDTO.getProductSize().getId()).get();
//            productSize.setQuantity(productSize.getQuantity()-orderDetailDTO.getQuantity());
//            if(productSize.getQuantity()==0){
//                productSize.setStatus(EStatusProduct.OutOfStock);
//            }
//            productSizeRepository.save(productSize);
            orderDetail.setProductSize(productSize);
            orderDetail.setQuantity(orderDetailDTO.getQuantity());
            orderDetail.setOrder(order);
            orderDetailRepository.save(orderDetail);
        }

        cartRepository.deleteByAccountId(orderRequest.getAccountId());

        OrderDTO orderDTO = new OrderDTO(order);
        return orderDTO;
    }

    @Override
    public List<OrderDTO> getOrderListByAccount(Long accountId){
        List<Order> orders =  orderRepository.findAllByAccountId(accountId);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Order order: orders){
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    @Override
    public OrderDTO getOrderById(Long orderId){
        Order order = orderRepository.findById(orderId).get();
        OrderDTO orderDTO = new OrderDTO(order);
        return orderDTO;
    }
    @Override
    public Page<OrderDTO> getOrderList(int page, int size){
        Page<Order> orderPage =  orderRepository.findAll(PageRequest.of(page,size));
        List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Order order: orders){
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOList.add(orderDTO);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<OrderDTO> productDTOPage = new PageImpl<>(orderDTOList,pageable,orderPage.getTotalElements());
        return productDTOPage;
    }

    @Override
    public OrderDTO updateStatus(Long orderId, ChangeStatusReq status){
        Order order = orderRepository.findById(orderId).get();
        if(status.getStatus() == EStatusOrder.SHIPPING){
            for (OrderDetail orderDetail: order.getOrderDetails()){
                if (orderDetail.getQuantity() > orderDetail.getProductSize().getQuantity()){
                    throw new BadRequestException("There are some products that are out of stock!");
                }
                ProductSize productSize = productSizeRepository.findById(orderDetail.getProductSize().getId()).get();
                productSize.setQuantity(productSize.getQuantity()-orderDetail.getQuantity());
                if(productSize.getQuantity()==0){
                    productSize.setStatus(EStatusProduct.OutOfStock);
                }
                productSizeRepository.save(productSize);
            }
            order.setStatus(status.getStatus());
        } else order.setStatus(status.getStatus());

        orderRepository.save(order);
        OrderDTO orderDTO = new OrderDTO(order);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> getOrderListByStatus(int page, int size, EStatusOrder status){
        Page<Order> orderPage =  orderRepository.findAllByStatus(PageRequest.of(page,size), status);
        List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Order order: orders){
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOList.add(orderDTO);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    public Page<OrderDTO> getOrderListByDateAsc(int page, int size){
       Page<Order> orderPage = orderRepository.findAllByOrderByCreateDateAsc(PageRequest.of(page,size));
       List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Order order: orders){
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOList.add(orderDTO);
        }
       Pageable pageable = PageRequest.of(page,size);
       Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderPage.getTotalElements());
       return orderDTOPage;
    }

    @Override
    public Page<OrderDTO> getOrderListByDateDesc(int page, int size){
        Page<Order> orderPage = orderRepository.findAllByOrderByCreateDateDesc(PageRequest.of(page,size));
        List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Order order: orders){
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOList.add(orderDTO);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    public Page<OrderDTO> getOrderListByStatusDateAsc(int page, int size, EStatusOrder status){
        Page<Order> orderPage = orderRepository.findAllByStatusOrderByCreateDateAsc(PageRequest.of(page,size),status);
        List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Order order: orders){
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOList.add(orderDTO);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    public Page<OrderDTO> getOrderListByStatusDateDesc(int page, int size, EStatusOrder status){
        Page<Order> orderPage = orderRepository.findAllByStatusOrderByCreateDateDesc(PageRequest.of(page,size),status);
        List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Order order: orders){
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOList.add(orderDTO);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    public Page<OrderDTO> getOrderListById(int page,int size,Long id){
        Page<Order> orderPage = orderRepository.findAllById(PageRequest.of(page,size),id);
        List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Order order: orders){
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOList.add(orderDTO);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    public List<Double> getRevenueDaily(int month, int year){
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        List<Object[]> results = orderRepository.getDailyRevenueByMonth(month, year);

        List<Integer> allDays = new ArrayList<>();

        for(int i = 1; i <= daysInMonth; i++) {
            allDays.add(i);
        }

        List<Double> revenues = new ArrayList<>();

        for(Integer day : allDays) {
            Double revenue = 0d; // mặc định là 0

            // Tìm xem ngày này có tồn tại trong kết quả từ DB không
            Object[] resultForDay = results.stream()
                    .filter(r -> day.equals((Integer)r[0]))
                    .findFirst().orElse(null);

            if(resultForDay != null) {
                // Nếu tồn tại lấy ra doanh thu
                revenue = (Double) resultForDay[1];
            }

            revenues.add(revenue);
        }
        return revenues;
    }

    @Override
    public List<Object[]> getBrandRevenueMonthly(int month, int year){
        List<Object[]> results = orderRepository.getRevenueByBrand(month, year);
        return results;
    }

    @Override
    public List<ProductRevenueDTO> getProductRevenueMonthly(String brand, int month, int year){
        List<ProductRevenueDTO> results = orderRepository.getRevenueByProduct(brand,month, year);
        List<Product> products = productRepository.findAllByBrand(brand);
        List<String> productNames = products.stream()
                .map((Product::getName))
                .toList();
        for (String name : productNames){
            if (!containsName(results,name)) {
                results.add(new ProductRevenueDTO(name,0.0));
            }
        }
        System.out.println(results);
        return results;
    }

    public boolean containsName(final List<ProductRevenueDTO> list, final String name){
        return list.stream().map(ProductRevenueDTO::getProductName).anyMatch(name::equals);
    }

    @Override
    public List<OrderDTO> getAllOrderList(){
        List<Order> orders = orderRepository.findAllNotCancelledOrders();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders){
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    @Override
    public List<Object[]> statisticStatusOrder(int month, int year){
        List<Object[]> result = orderRepository.countOrderByStatusEachMonth(month,year);
        return result;
    }

    @Override
    public List<Object[]> getTop10AccountConsume(int month, int year){
        List<Object[]> result = orderRepository.top10AccountConsume(month,year);
        return result;
    }


}
