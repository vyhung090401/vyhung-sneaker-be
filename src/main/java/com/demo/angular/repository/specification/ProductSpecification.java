package com.demo.angular.repository.specification;

import com.demo.angular.model.Inventory;
import com.demo.angular.model.InventoryProduct;
import com.demo.angular.model.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> filterByPriceRanges(List<List<Double>> priceRange){
        System.out.println(priceRange);
        return (root,query,criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (List<Double> range : priceRange){
                if(!range.isEmpty()){
                    Double low = range.get(0);
                    Double high = range.get(1);
                    predicates.add(
                            criteriaBuilder.between(root.get("price"),low,high)
                    );
                }

            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
    public static Specification<Product> filterByBrand(List<String> brandList){
        System.out.println(brandList);
        return (root,query,criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String brandName : brandList){
                if(!brandName.isEmpty()){
                    predicates.add(
                            criteriaBuilder.equal(root.get("brand"),brandName)
                    );
                }

            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

}
