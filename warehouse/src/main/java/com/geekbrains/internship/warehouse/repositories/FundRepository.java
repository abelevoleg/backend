package com.geekbrains.internship.warehouse.repositories;

import com.geekbrains.internship.warehouse.entities.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundRepository extends JpaRepository<Fund, Long> {
}
