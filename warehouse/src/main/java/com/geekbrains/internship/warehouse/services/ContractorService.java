package com.geekbrains.internship.warehouse.services;

import com.geekbrains.internship.warehouse.entities.Contractor;
import com.geekbrains.internship.warehouse.entities.Product;
import com.geekbrains.internship.warehouse.entities.ProductTransaction;
import com.geekbrains.internship.warehouse.repositories.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractorService {
    private ContractorRepository contractorRepository;
    private ProductTransactionService productTransactionService;
    @Autowired
    public ContractorService(ContractorRepository contractorRepository, ProductTransactionService productTransactionService){
        this.contractorRepository = contractorRepository;
        this.productTransactionService = productTransactionService;
    }

    public List<Contractor> findAll() {
        return contractorRepository.findAll();
    }

    public Contractor saveOrUpdate(Contractor contractor) {
        return contractorRepository.save(contractor);
    }

    public void deleteById(Long id) {
        contractorRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return contractorRepository.existsById(id);
    }

    public List<Contractor> getProvidersByProduct(Product product){
        List<Contractor> list = new ArrayList<>();
        for (ProductTransaction supply :productTransactionService.getSuppliesByProduct(product)) {
            list.add(supply.getContractor());
        }
        return list;
    }
}
