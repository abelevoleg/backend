package com.geekbrains.internship.warehouse.repositories;

import com.geekbrains.internship.warehouse.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    Unit findByTitle(String title);
    boolean existsByTitle(String title);
}
