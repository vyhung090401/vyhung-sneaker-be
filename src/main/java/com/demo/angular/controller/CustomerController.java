package com.demo.angular.controller;


import com.demo.angular.dto.CustomerDTO;
import com.demo.angular.model.Account;
import com.demo.angular.model.Customer;
import com.demo.angular.repository.CustomerRepository;
import com.demo.angular.service.CustomerService;
import com.demo.angular.serviceImp.CustomerServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerServiceImp customerServiceImp;

    @Autowired
    private CustomerService customerService;


    //get all products
    @GetMapping("/customers")
    public ResponseEntity<Page<Customer>> getCusList(@RequestParam(name = "name", defaultValue = "") String name,
                                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                                         @RequestParam(name = "size", defaultValue = "5") int size) {

        Page<Customer> customers = customerServiceImp.getCustomers(name,page, size);
        return ResponseEntity.ok(customers);
    }
    @PutMapping("/customers")
    public ResponseEntity<?> updateCustomerInf(@RequestBody CustomerDTO customer){
        CustomerDTO customerDTO = customerService.updateCustomer(customer);
        return ResponseEntity.ok(customerDTO);
    }
}
