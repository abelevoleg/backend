package com.geekbrains.internship.warehouse.repositories;


import com.geekbrains.internship.warehouse.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByTitle(String title);
}
