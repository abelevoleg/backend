package com.geekbrains.internship.warehouse.repositories;

import com.geekbrains.internship.warehouse.entities.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UserActionRepository extends JpaRepository<UserAction, Long> {
    List<UserAction> findAllByProductId(Long productId);

    List<UserAction> findAllByAuthorName(String authorName);

    List<UserAction> findAllByData(Date data);
}
