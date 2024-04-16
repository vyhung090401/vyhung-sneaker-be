package com.demo.angular.service;

import com.demo.angular.dto.InventoryProductDTO;
import com.demo.angular.dto.ProductDTO;
import com.demo.angular.model.Account;
import com.demo.angular.model.EStatusProduct;
import com.demo.angular.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface ProductService {

    List<Product> getAllProducts();

    ProductDTO createProduct(ProductDTO productDTO, InventoryProductDTO inventoryProductDTO);

    Product getProduct(Long id);

    Product updateProduct(ProductDTO productDTO);

    void deleteProduct(Long id);


//    Page<ProductDTO> getProductsByBrand(List<String> brand, int page, int size);

    List<Product> getProductsByPriceRange(Double upperBound);

    List<ProductDTO> getProductsByPriceLessThanAndPriceGreaterThan(Double maxPrice, Double minPrice);

    List<Product> getProductsByPriceGreaterThan(Double minPrice);

    List<Product> getProductsByBrandAndPriceGreaterThan(String brand,Double upperBound);

    List<Product> getProductsByBrandAndPriceLessThan(String brand,Double upperBound);

    List<Product> searchProductsByName(String keyword);

    List<Product> searchProductsByBrand(String keyword);

    List<Product> searchProductsByColor(String keyword);

//    List<Product> searchByFields(String name, Double price, Integer amount, String color,String brand);

    Page<ProductDTO> getProducts(int page, int size);

    Page<ProductDTO> searchProductsByKeyword(String keyword,int page, int size);

    ProductDTO  getProductById(Long id);

    Page<ProductDTO> getAvailableProducts(int page, int size);

    Page<ProductDTO> findProductsByFilter(List<List<Double>> priceRanges,
                                          List<String> brandList,
                                          int page,
                                          int size);

    Page<ProductDTO> findProductsByPriceRanges(List<List<Double>> priceRanges, int page, int size);

    Page<ProductDTO> findProductsByBrand(List<String> brand, int page, int size);

    Page<ProductDTO> findProductsByAdvanceFilter(Double minPrice,
                                                 Double maxPrice,
                                                 String inventoryName,
                                                 String productName,
                                                 List<String> brandList,
                                                 List<String> colorList,
                                                 List<EStatusProduct> statusList,
                                                 int page,
                                                 int size);
}
