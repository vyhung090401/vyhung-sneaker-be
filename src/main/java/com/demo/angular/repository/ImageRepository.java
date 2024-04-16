package com.demo.angular.repository;

import com.demo.angular.model.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByProductId(Long id);
    @Query("select count(i) from Image i where i.product.id=?1")
    Integer getSizeByProductId(Long productId);
    @Transactional
    @Modifying
    @Query("delete from Image i where i.product.id=?1")
    void deleteByProductId(Long id);
}
