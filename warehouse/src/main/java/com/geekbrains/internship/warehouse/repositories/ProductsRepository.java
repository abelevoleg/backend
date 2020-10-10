package com.geekbrains.internship.warehouse.repositories;

import com.geekbrains.internship.warehouse.entities.Product;
import com.geekbrains.internship.warehouse.entities.dtos.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<ProductDto> findAllBy();
    Product findByTitle(String title);
}