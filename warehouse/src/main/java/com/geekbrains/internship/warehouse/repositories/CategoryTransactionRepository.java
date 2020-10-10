package com.geekbrains.internship.warehouse.repositories;

import com.geekbrains.internship.warehouse.entities.CategoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CategoryTransactionRepository extends JpaRepository<CategoryTransaction, Long>{
    List<CategoryTransaction> findAllByCategoryId(Long categoryId);

    List<CategoryTransaction> findAllByAuthorName(String authorName);

    List<CategoryTransaction> findAllByData(Date data);
}
