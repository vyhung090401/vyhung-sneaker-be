package com.demo.angular.dto;

import com.demo.angular.model.Customer;
import com.demo.angular.model.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {

    public CustomerDTO(Customer customer){
        this.phone = customer.getPhone();
        this.gender = customer.getGender();
        this.birthday = customer.getBirthday();
        this.address = customer.getAddress();
        this.id = customer.getAccount().getId();
    }

    private String phone;
    private Customer.Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String address;
    private Long id;
    private List<String> roles;
    private String username;
}
