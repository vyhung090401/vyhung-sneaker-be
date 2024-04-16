package com.demo.angular.repository;

import com.demo.angular.model.Account;
import com.demo.angular.model.InventoryProduct;
import com.demo.angular.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryProductRepository extends JpaRepository<InventoryProduct, Long> {

    Page<InventoryProduct> findAll(Pageable pageable);

    InventoryProduct findByProductId(Long id);
}
