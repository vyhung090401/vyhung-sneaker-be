package com.demo.angular.repository;

import com.demo.angular.dto.ProductRevenueDTO;
import com.demo.angular.model.EStatusOrder;
import com.demo.angular.model.Order;
import com.demo.angular.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByAccountId(Long accountId);
    List<Order> findAllById(Long accountId);

    Page<Order> findAll(Pageable pageable);

    Page<Order> findAllByStatus(Pageable pageable, EStatusOrder status);

    Page<Order> findAllByOrderByCreateDateAsc(Pageable pageable);

    Page<Order> findAllByOrderByCreateDateDesc(Pageable pageable);

    Page<Product> findAll(Specification<Order> spec, Pageable pageable);

    Page<Order> findAllByStatusOrderByCreateDateAsc(Pageable pageable, EStatusOrder status);

    Page<Order> findAllByStatusOrderByCreateDateDesc(Pageable pageable, EStatusOrder status);

    Page<Order> findAllById(Pageable pageable, Long id);

    @Query("SELECT DAY(o.createDate) as day, SUM(o.orderTotal) as revenue " +
            "FROM Order o " +
            "WHERE MONTH(o.createDate) = :month AND YEAR(o.createDate) = :year " +
            "AND o.status <> 3 " +
            "GROUP BY DAY(o.createDate)")
    List<Object[]> getDailyRevenueByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT p.brand, SUM(od.totalPrice) " +
            "FROM Order o JOIN o.orderDetails od " +
            "JOIN od.productSize ps JOIN ps.product p " +
            "WHERE MONTH(o.createDate) = :month AND YEAR(o.createDate) = :year " +
            "AND o.status <> 3 " +
            "GROUP BY p.brand")
    List<Object[]> getRevenueByBrand(@Param("month") int month, @Param("year") int year);

    @Query("SELECT new com.demo.angular.dto.ProductRevenueDTO(p.name,SUM(od.totalPrice)) " +
            "FROM Order o JOIN o.orderDetails od " +
            "JOIN od.productSize ps JOIN ps.product p " +
            "WHERE p.brand = :brand " +
            "AND MONTH(o.createDate) = :month AND YEAR(o.createDate) = :year " +
            "AND o.status <> 3 " +
            "GROUP BY p.name")
    List<ProductRevenueDTO> getRevenueByProduct(@Param("brand") String brand,
                                                @Param("month") int month,
                                                @Param("year") int year);

    @Query("SELECT o FROM Order o WHERE o.status <> 3 ")
    List<Order> findAllNotCancelledOrders();

    @Query("SELECT o.status, COUNT(o.status) " +
            "FROM Order o " +
            "WHERE MONTH(o.createDate) = :month AND YEAR(o.createDate) = :year " +
            "GROUP BY o.status")
    List<Object[]> countOrderByStatusEachMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT a.username , SUM(o.orderTotal) as totalSpending " +
            "FROM Account a " +
            "INNER JOIN Order o ON a.id = o.account.id " +
            "WHERE MONTH(o.createDate) = :month  " +
            "AND YEAR(o.createDate) = :year AND o.status <> 3 " +
            "GROUP BY a.username " +
            "ORDER BY totalSpending DESC " +
            "LIMIT 10")
    List<Object[]> top10AccountConsume(@Param("month") int month, @Param("year") int year);
}
