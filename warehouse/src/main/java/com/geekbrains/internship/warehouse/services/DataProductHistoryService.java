package com.geekbrains.internship.warehouse.services;

import com.geekbrains.internship.warehouse.entities.DataProductHistory;
import com.geekbrains.internship.warehouse.repositories.DataProductHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataProductHistoryService {
    private DataProductHistoryRepository dataProductHistoryRepository;

    @Autowired
    public void setDataProductHistoryRepository(DataProductHistoryRepository dataProductHistoryRepository) {
        this.dataProductHistoryRepository = dataProductHistoryRepository;
    }

    public List<DataProductHistory> getAllHistory() {
        return dataProductHistoryRepository.findAll();
    }

    public List<DataProductHistory> getProductHistory(Long productsId) {
        return dataProductHistoryRepository.findAllByProductsId(productsId);
    }

    public boolean existsById(Long id) {
        return dataProductHistoryRepository.existsById(id);
    }

    public DataProductHistory saveOrUpdate(DataProductHistory dataProductHistory) {
        return dataProductHistoryRepository.save(dataProductHistory);
    }

    public void deleteById(Long id) {
        dataProductHistoryRepository.deleteById(id);
    }
}
