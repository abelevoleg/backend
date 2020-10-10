package com.geekbrains.internship.warehouse.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "deleted_users")
@Data
@NoArgsConstructor
public class DeletedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinTable(name = "link__deleted_users",
            joinColumns = @JoinColumn(name = "deleted_user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;
}
