package com.geekbrains.internship.warehouse.repositories;

import com.geekbrains.internship.warehouse.entities.Contractor;
import com.geekbrains.internship.warehouse.entities.Product;
import com.geekbrains.internship.warehouse.entities.ProductTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTransactionRepository extends JpaRepository<ProductTransaction, Long>{
    List<ProductTransaction> findAllByProductId(Long productId);
    List<ProductTransaction> findByQuantityGreaterThan(Double quantity);
    List<ProductTransaction> findByQuantityLessThan(Double quantity);
    List<ProductTransaction> findByProduct(Product product);
    List<ProductTransaction> findByProductAndQuantityGreaterThan(Product product, Double quantity);

    List<ProductTransaction> findByContractor(Contractor contractor);
}