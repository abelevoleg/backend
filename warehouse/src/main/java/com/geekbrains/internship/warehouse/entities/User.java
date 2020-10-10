package com.geekbrains.internship.warehouse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String login;

    //@JsonIgnore
    @Column
    private String password;

    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String phone;
    @Column
    private String email;

    @ManyToMany
    @JoinTable(name = "link__users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Transient
    @JsonIgnore
    private String[] roleNames;
}
