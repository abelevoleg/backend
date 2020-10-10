package com.geekbrains.internship.warehouse.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "view__funds")
@Getter
@NoArgsConstructor
public class Fund {
    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinTable(name = "products",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Product product;

    @Column(name = "quantity")
    private Double balance;
}
