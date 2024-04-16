package com.demo.angular.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Size extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Long id;

    @Column(name="size_number")
    Integer sizeNumber;

    @OneToMany(mappedBy = "size")
    private List<ProductSize> productSizes;

}
