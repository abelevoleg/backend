package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.entities.Category;
import com.geekbrains.internship.warehouse.entities.dtos.ErrorDto;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import com.geekbrains.internship.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.internship.warehouse.services.CategoriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Api("Set of endpoints for CRUD operations for Category")
public class CategoriesController {
    private CategoriesService categoriesService;

    @Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all categories")
    public List<Category> getAllCategories() {
        return categoriesService.getAllCategories();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns one category by id")
    public ResponseEntity<?> getOneCategory(@PathVariable Long id) {
        if (!categoriesService.existsById(id)) {
            throw new ProductNotFoundException("Category not found, id: " + id);
        }
        return new ResponseEntity<>(categoriesService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one category by id")
    public List<Category> deleteOneProducts(@PathVariable Long id) {
        categoriesService.deleteById(id);
        return categoriesService.getAllCategories();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new category")
    public Category saveNewCategory(@RequestBody Category category) {
        if (category.getId() != null) {
            category.setId(null);
        }
        return categoriesService.saveOrUpdate(category);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing category")
    public Category modifyCategory(@PathVariable Long id, @RequestBody Category category) {
        if (id == null || !categoriesService.existsById(id)) {
            throw new ProductNotFoundException("Product not found, id: " + id);
        }
        category.setId(id);
        return categoriesService.saveOrUpdate(category);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(CustomException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), exception.getStatus());
    }
}
