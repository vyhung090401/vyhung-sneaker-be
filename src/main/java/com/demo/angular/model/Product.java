package com.demo.angular.model;

import com.demo.angular.dto.ImageDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product")
public class Product extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Long id;
    @Column(name="name")
    String name;
    @Column(name="price")
    Double price;
    @Column(name="color")
    String color;
    @Column(name="brand")
    String brand;
    @Column(name="status")
    EStatusProduct status;
    @Column(name="quantity")
    Integer quantity;

    @OneToMany(mappedBy = "product")
    private List<ProductSize> productSizes = new ArrayList<>();

    @OneToMany(mappedBy = "product",targetEntity = Image.class, fetch = FetchType.LAZY)
    List<Image> images = new ArrayList<>();

    public String toString() {
        return "";
    }

}
