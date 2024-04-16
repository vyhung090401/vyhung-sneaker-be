package com.demo.angular.serviceImp;


import com.demo.angular.dto.CustomerDTO;
import com.demo.angular.model.Account;
import com.demo.angular.model.Customer;
import com.demo.angular.model.Role;
import com.demo.angular.repository.AccountRepository;
import com.demo.angular.repository.CustomerRepository;
import com.demo.angular.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImp implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Page<Customer> getCustomers(String keyword, int page, int size) {
        return customerRepository.findByKeyword(keyword, PageRequest.of(page, size));
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO){
        System.out.println(customerDTO.getBirthday());
        Account account = accountRepository.findById(customerDTO.getId()).get();
        Customer customer = customerRepository.findByAccountId(customerDTO.getId());
        customer.setBirthday(customerDTO.getBirthday());
        customer.setAddress(customerDTO.getAddress());
        customer.setPhone(customerDTO.getPhone());
        customer.setGender(customerDTO.getGender());
        customerRepository.save(customer);
        CustomerDTO customerDTOUpdate = new CustomerDTO(customer);
        List<String> roles = new ArrayList<>();
        Set<Role> roles1 = account.getRoles();
        for (Role role : roles1){
            roles.add(role.getName().name());
        }
        customerDTOUpdate.setUsername(account.getUsername());
        customerDTOUpdate.setRoles(roles);
        System.out.println(customerDTOUpdate.getBirthday());
        return customerDTOUpdate;
    }

}