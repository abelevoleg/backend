package com.geekbrains.internship.warehouse.services;

import com.geekbrains.internship.warehouse.entities.Category;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import com.geekbrains.internship.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.internship.warehouse.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriesService {
    private CategoriesRepository categoriesRepository;

    @Autowired
    public void setCategoriesRepository(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<Category> getAllCategories() {
        return categoriesRepository.findAll();
    }

    public List<Category> getCategoriesByIds(List<Long> ids) {
        return categoriesRepository.findAllById(ids);
    }

    public Category saveOrUpdate(Category category) {
        try {
            Category savedCategory = categoriesRepository.findByTitle(category.getTitle());
            return  savedCategory == null ? categoriesRepository.save(category) : savedCategory;
        }catch(RuntimeException e){
            throw new CustomException("Не удалось сохранить изменения: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    public List<Category> saveOrUpdate(List<Category> categories) {
        List<Category> list = new ArrayList<>();
        for (Category category : categories) {
            list.add(saveOrUpdate(category));
        }
        return list;
    }
    public Category findById(Long id) {
        return categoriesRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Can't found category with id = " + id));
    }

    public void deleteById(Long id) {
        categoriesRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return categoriesRepository.existsById(id);
    }
}
