package com.geekbrains.internship.warehouse.services;

import com.geekbrains.internship.warehouse.entities.Role;
import com.geekbrains.internship.warehouse.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RolesRepository rolesRepository;

    public List<Role> findAll() {
        return (List<Role>) rolesRepository.findAll();
    }
}
