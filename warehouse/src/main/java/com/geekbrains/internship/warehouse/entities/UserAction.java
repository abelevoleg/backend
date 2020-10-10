package com.geekbrains.internship.warehouse.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_actions")
public class UserAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "data")
    private Date data;

    @Column(name = "author")
    private String authorName;

    public UserAction(Long id, String type, Long productId, String productName, String authorName) {
        this.id = id;
        this.type = type;
        this.productId = productId;
        this.productName = productName;
        this.data = new Date();
        this.authorName = authorName;
    }
}
