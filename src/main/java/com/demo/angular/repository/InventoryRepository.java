package com.demo.angular.repository;

import com.demo.angular.model.Inventory;
import com.demo.angular.model.InventoryProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Page<Inventory> findAll(Pageable pageable);
    Inventory findByName(String inventoryName);

}
