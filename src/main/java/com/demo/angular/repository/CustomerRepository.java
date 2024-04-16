package com.demo.angular.repository;

import com.demo.angular.model.Account;
import com.demo.angular.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Boolean existsByPhone(String phone);

    Customer findByAccountId(Long accountId);

    Customer findByAccount (Account account);

    @Query("SELECT a FROM Account a WHERE a.username LIKE %:keyword%")
    Page<Customer> findByKeyword(@Param("keyword") String keyword, Pageable pageable);


}
