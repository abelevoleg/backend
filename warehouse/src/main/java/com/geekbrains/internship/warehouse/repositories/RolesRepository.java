package com.geekbrains.internship.warehouse.repositories;


import com.geekbrains.internship.warehouse.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends CrudRepository<Role, Long> {
	Role findOneByName(String name);
}
