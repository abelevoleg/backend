package com.geekbrains.internship.warehouse.repositories;

import com.geekbrains.internship.warehouse.entities.DataProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface DataProductHistoryRepository extends JpaRepository<DataProductHistory, Long> {
    List<DataProductHistory> findAllByProductsId(Long productsId);
}
