package com.geekbrains.july.warehouse.repositories;


import com.geekbrains.july.warehouse.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByTitle(String title);
}