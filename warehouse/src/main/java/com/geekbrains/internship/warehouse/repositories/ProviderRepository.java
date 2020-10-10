package com.geekbrains.internship.warehouse.repositories;

import com.geekbrains.internship.warehouse.entities.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



    @Repository
    public interface ProviderRepository extends JpaRepository<Provider, Long> {
    }
