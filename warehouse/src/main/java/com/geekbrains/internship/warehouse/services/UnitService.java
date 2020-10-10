package com.geekbrains.internship.warehouse.services;

import com.geekbrains.internship.warehouse.entities.Unit;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import com.geekbrains.internship.warehouse.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {
    @Autowired
    private UnitRepository unitRepository;

    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    public Unit saveOrUpdate(Unit unit) {
        try {
            Unit savedUnit = findByTitle(unit.getTitle());
            return savedUnit == null ? unitRepository.save(unit) : savedUnit;
        }catch(Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    public Unit findByTitle(String title){
        return unitRepository.findByTitle(title);
    }
    public void deleteById(Long id) {
        unitRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return unitRepository.existsById(id);
    }
    public boolean existsByTitle(String title) {
        return unitRepository.existsByTitle(title);
    }
}
