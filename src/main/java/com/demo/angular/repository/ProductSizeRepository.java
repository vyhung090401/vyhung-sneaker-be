package com.demo.angular.repository;

import com.demo.angular.model.Account;
import com.demo.angular.model.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    // Lấy size nam
    List<ProductSize> findAllByProductId(Long productId);
}
