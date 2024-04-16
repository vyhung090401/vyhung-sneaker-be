package com.demo.angular.repository;

import com.demo.angular.model.InventoryProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryProductSizeRepository extends JpaRepository<InventoryProductSize, Long> {

    List<InventoryProductSize> findAllByProductSizeProductId (Long productId);

    List<InventoryProductSize> findAllByProductId (Long productId);

}
