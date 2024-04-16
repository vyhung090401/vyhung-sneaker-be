package com.demo.angular.dto;

import com.demo.angular.model.EStatusOrder;
import com.demo.angular.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {

    public OrderDTO(Order order){
        this.id = order.getId();
        this.name = order.getName();
        this.address = order.getAddress();
        this.email = order.getEmail();
        this.phoneNumber = order.getPhoneNumber();
        this.accountId = order.getAccount().getId();
        this.orderTotal = order.getOrderTotal();
        this.createDate = order.getCreateDate();
        this.status = order.getStatus();

    }
    private Long id ;

    private Double orderTotal ;

    private String name;

    private String address;

    private String email;

    private String phoneNumber;

    private Long accountId;

    private Date createDate;

    private EStatusOrder status;

    private List<OrderDetailDTO> orderDetailList;


}
