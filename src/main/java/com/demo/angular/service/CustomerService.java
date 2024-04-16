package com.demo.angular.service;

import com.demo.angular.dto.CustomerDTO;
import com.demo.angular.model.Account;
import com.demo.angular.model.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    public Page<Customer> getCustomers(String keyword, int page, int size);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);
}
