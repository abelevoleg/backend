package com.geekbrains.internship.warehouse.repositories;

import com.geekbrains.internship.warehouse.entities.DeletedUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeletedUsersRepository extends CrudRepository<DeletedUser, Long> {
}
