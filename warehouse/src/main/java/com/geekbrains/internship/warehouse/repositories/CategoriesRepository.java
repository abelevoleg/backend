package com.geekbrains.internship.warehouse.repositories;

import com.geekbrains.internship.warehouse.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Category findByTitle(String title);
}