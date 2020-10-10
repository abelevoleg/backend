package com.geekbrains.internship.warehouse.services;

import com.geekbrains.internship.warehouse.entities.CategoryTransaction;
import com.geekbrains.internship.warehouse.repositories.CategoryTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryTransactionService {
    private CategoryTransactionRepository categoryTransactionRepository;

    @Autowired
    public void setProductTransactionRepository(CategoryTransactionRepository categoryTransactionRepository) {
        this.categoryTransactionRepository = categoryTransactionRepository;
    }

    public List<CategoryTransaction> getAllTransactions() {
        return categoryTransactionRepository.findAll();
    }

    public List<CategoryTransaction> getCategoryTransactions(Long categoryId) {
        return categoryTransactionRepository.findAllByCategoryId(categoryId);
    }

    public List<CategoryTransaction> getAuthorTransactions(String authorName) {
        return categoryTransactionRepository.findAllByAuthorName(authorName);
    }

    public List<CategoryTransaction> getDateTransactions(Date data) {
        return categoryTransactionRepository.findAllByData(data);
    }

    public CategoryTransaction saveOrUpdate(CategoryTransaction categoryTransaction) {
        return categoryTransactionRepository.save(categoryTransaction);
    }
}
