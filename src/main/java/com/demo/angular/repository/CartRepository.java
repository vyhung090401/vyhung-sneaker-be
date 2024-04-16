package com.demo.angular.repository;

import com.demo.angular.dto.CartDTO;
import com.demo.angular.model.Account;
import com.demo.angular.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT c FROM Cart c WHERE account.id = ?1")
    List<Cart> findCartById(Long accountId);
    Cart findByAccountIdAndProductSizeId(Long accountId, Long productSizeId);

    void deleteByAccountId(Long accountId);

}
