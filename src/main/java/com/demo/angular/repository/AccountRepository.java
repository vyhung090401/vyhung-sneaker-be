package com.demo.angular.repository;

import com.demo.angular.model.Account;
import com.demo.angular.model.Customer;
import com.demo.angular.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Định nghĩa các phương thức truy vấn tùy chỉnh (nếu cần)
//    @Query("SELECT a FROM Account a WHERE a.username LIKE %:keyword%")
//    Page<Account> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Page<Account> findAll(Pageable pageable);

    Account findByUsername(@Param("username") String username);

    Boolean existsByUsername(String username);

    Account findByCustomerPhone(String phoneNumber);

}
