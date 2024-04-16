package com.demo.angular.dto;

import com.demo.angular.model.Account;
import com.demo.angular.model.Cart;
import com.demo.angular.model.Customer;
import com.demo.angular.model.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AccountDTO implements Serializable {

    public AccountDTO(Account account){
        this.id = account.getId();
        this.username = account.getUsername();
        this.roles = account.getRoles();
        this.isActive = account.isActive();
        this.isNotLocked = account.isNotLocked();

    }

    private boolean isActive;

    private boolean isNotLocked;

    private Set<Role> roles = new HashSet<>();

    private Long id;

    private String username;

    private String phone;

}
