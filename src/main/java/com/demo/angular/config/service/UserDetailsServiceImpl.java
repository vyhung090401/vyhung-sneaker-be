package com.demo.angular.config.service;


import com.demo.angular.model.Account;
import com.demo.angular.model.Customer;
import com.demo.angular.repository.AccountRepository;

import com.demo.angular.repository.CustomerRepository;
import com.demo.angular.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountService accountService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        Customer customer = customerRepository.findByAccount(account);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        if(!account.isNotLocked()){
            if (accountService.unlockWhenTimeExpired(account)) {
                System.out.println("okokok");
                throw new LockedException("Your account has been unlocked. Please try to login again.");
            }
            throw new LockedException("Username has been locked");
        }
        return UserDetailsImp.build(account,customer);
    }


}
