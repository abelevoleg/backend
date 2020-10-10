package com.geekbrains.internship.warehouse.repositories;

import com.geekbrains.internship.warehouse.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    Optional<User> findOneByLogin(String login);
    boolean existsByLogin(String login);
}