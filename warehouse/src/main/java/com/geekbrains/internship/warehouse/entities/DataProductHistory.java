package com.geekbrains.internship.warehouse.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products_history")
@Data
@NoArgsConstructor
public class DataProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "products_id")
    private Long productsId;

    @Column(name = "quantity")
    private int quantity;

    public DataProductHistory(Long id, Long productsId, int quantity) {
        this.id = id;
        this.productsId = productsId;
        this.quantity = quantity;
    }
}

