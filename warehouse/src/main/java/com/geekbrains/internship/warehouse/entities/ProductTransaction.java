package com.geekbrains.internship.warehouse.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_transactions")
@Data
@NoArgsConstructor
public class ProductTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @ManyToOne
    @JoinTable(name = "link__transactions_products",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Product product;

    @ManyToOne
    @JoinTable(name = "link__transactions_contractors",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "contractor_id"))
    private Contractor contractor;

    @Column(name="quantity")
    private double quantity;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinTable(name = "link__transactions_users",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;
}
