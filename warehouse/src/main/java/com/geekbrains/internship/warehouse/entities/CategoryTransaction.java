package com.geekbrains.internship.warehouse.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "categories_transaction")
@Data
@NoArgsConstructor
@Setter
@Getter
public class CategoryTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;

    @Column(name = "author")
    private String authorName;

    public CategoryTransaction(Long id, String type, Long categoryId, String authorName) {
        this.id = id;
        this.type = type;
        this.categoryId = categoryId;
        this.data = new Date();
        this.authorName = authorName;
    }
}

