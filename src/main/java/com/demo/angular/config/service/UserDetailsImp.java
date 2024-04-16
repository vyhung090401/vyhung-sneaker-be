package com.demo.angular.config.service;

import com.demo.angular.model.Account;
import com.demo.angular.model.Customer;
import com.demo.angular.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class UserDetailsImp implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String phone;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String address;
    private Set<Role> roles;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isNotLocked;
    private Customer.Gender gender;


    public UserDetailsImp(Long id, String username, String password, String phone, Set<Role> roles, Date birthday, String address,
                          Collection<? extends GrantedAuthority> authorities, boolean isNotLocked, Customer.Gender gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.roles = roles;
        this.birthday = birthday;
        this.address = address;
        this.authorities = authorities;
        this.isNotLocked=isNotLocked;
        this.gender = gender;
    }

    public static UserDetailsImp build(Account account, Customer customer) {
        List<GrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImp(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                customer.getPhone(),
                account.getRoles(),
                customer.getBirthday(),
                customer.getAddress(),
                authorities,
                account.isNotLocked(),
                customer.getGender());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImp user = (UserDetailsImp) o;
        return Objects.equals(id, user.id);
    }
}
