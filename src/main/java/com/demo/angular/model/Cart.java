package com.demo.angular.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cart")
public class Cart extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_size_id")
    private ProductSize productSize;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "quantity")
    private Integer quantity;

    public Cart(ProductSize productSize, Account account) {
        this.productSize = productSize;
        this.account = account;
    }


}