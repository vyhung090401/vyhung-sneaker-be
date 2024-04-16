package com.demo.angular.repository;

import com.demo.angular.dto.ProductDTO;
import com.demo.angular.model.EStatusProduct;
import com.demo.angular.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT DISTINCT brand FROM Product")
    List<String> getAllBrands();

    @Query("SELECT DISTINCT color FROM Product")
    List<String> getAllColors();

    // Trong interface ProductRepository
    @Query("SELECT p FROM Product p WHERE p.price < :upperBound OR p.price >= 500")
    List<Product> getProductsByPriceRange(@Param("upperBound") Double upperBound);


//    @Query("SELECT p FROM Product p WHERE p.brand IN :brands")
    List<Product> findAllByBrand(@Param("brands") String brands);

    List<Product> findByPriceLessThanAndPriceGreaterThan(@Param("maxPrice") Double maxPrice, @Param("minPrice") Double minPrice);

    @Query("SELECT p FROM Product p WHERE p.price >= :minPrice")
    List<Product> findByPriceGreaterThan(@Param("minPrice") Double minPrice);

    @Query("SELECT p FROM Product p WHERE p.brand = :brand AND p.price < :upperBound")
    List<Product> getProductsByBrandAndPriceLessThan(@Param("brand") String brand, @Param("upperBound") Double upperBound);

    @Query("SELECT p FROM Product p WHERE p.brand = :brand AND p.price >= :upperBound")
    List<Product> getProductsByBrandAndPriceGreaterThan(@Param("brand") String brand, @Param("upperBound") Double upperBound);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% ")
    List<Product> findByKeywordByName(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.color LIKE %:keyword% ")
    List<Product> findByKeywordByColor(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.brand LIKE %:keyword%")
    List<Product> findByKeywordByBrand(@Param("keyword") String keyword);

//    @Query("SELECT p FROM Product p WHERE (:name IS NULL OR p.name LIKE %:name%) AND (:price IS NULL OR p.price = :price) AND (:amount IS NULL OR p.amount = :amount) AND (:color IS NULL OR p.color LIKE %:color%) AND (:brand IS NULL OR p.brand LIKE %:brand%) ")
//    List<Product> findByFields(@Param("name") String name,@Param("price") Double price,@Param("amount") Integer amount,@Param("color") String color,@Param("brand") String brand);


    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByQuantityGreaterThan(Integer qty,Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.brand LIKE %:keyword% ")
    Page<Product> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status = 0")
    Page<Product> findAllAvailableProducts(Pageable pageable);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    @Query("SELECT p FROM Product p INNER JOIN InventoryProduct ip ON p.id = ip.product.id " +
                                    "INNER JOIN Inventory i ON i.id = ip.inventory.id " +
                                    "WHERE ( :inventoryName IS NULL  OR i.name = :inventoryName ) " +
                                    "AND ( :productName IS NULL  OR p.name LIKE %:productName% ) " +
                                    "AND ( :colorList IS NULL OR p.color IN :colorList ) " +
                                    "AND ( :brandList IS NULL OR p.brand IN :brandList ) " +
                                    "AND ( :statusList IS NULL OR p.status IN :statusList ) " +
                                    "AND ( (:minPrice IS NULL OR :maxPrice IS NULL) OR ( p.price BETWEEN :minPrice AND :maxPrice )) " +
                                    "AND ( p.quantity > 0) order by p.id" )
    Page<Product> findByFilter(@Param("inventoryName") String inventoryName,
                               @Param("productName") String productName,
                               @Param("colorList") List<String> colorList,
                               @Param("brandList") List<String> brandList,
                               @Param("statusList") List<EStatusProduct> statusList,
                               @Param("minPrice") Double minPrice,
                               @Param("maxPrice") Double maxPrice,
                               Pageable pageable);
}
