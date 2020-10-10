package com.geekbrains.internship.warehouse.services;

import com.geekbrains.internship.warehouse.entities.Contractor;
import com.geekbrains.internship.warehouse.entities.Product;
import com.geekbrains.internship.warehouse.entities.ProductTransaction;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import com.geekbrains.internship.warehouse.repositories.ProductTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductTransactionService {
    private ProductTransactionRepository productTransactionRepository;
    private UsersService usersService;
    private FundService fundService;

    @Autowired
    public ProductTransactionService(ProductTransactionRepository productTransactionRepository, UsersService usersService, FundService fundService){
        this.productTransactionRepository = productTransactionRepository;
        this.usersService = usersService;
        this.fundService = fundService;
    }

    public List<ProductTransaction> getAllTransactions() {
        return productTransactionRepository.findAll();
    }

    public List<ProductTransaction> getSupplyTransactions() {
        return productTransactionRepository.findByQuantityGreaterThan(0.0);
    }
    public List<ProductTransaction> getShipmentTransactions() {
        List<ProductTransaction> shipments = productTransactionRepository.findByQuantityLessThan(0.0);
        for (ProductTransaction shipment : shipments) {
            shipment.setQuantity(Math.abs(shipment.getQuantity()));
        }
        return shipments;
    }
    public List<ProductTransaction> getTransactionsByProduct(Product product) {
        return productTransactionRepository.findByProduct(product);
    }
    public List<ProductTransaction> getSuppliesByProduct(Product product) {
        return productTransactionRepository.findByProductAndQuantityGreaterThan(product, 0.0);
    }
    public List<ProductTransaction> getTransactionsByContractor(Contractor contractor){
        return productTransactionRepository.findByContractor(contractor);
    }
    public ProductTransaction createSupply(ProductTransaction supply) {
        supply.setQuantity(Math.abs(supply.getQuantity()));
        return create(supply);
    }
    public ProductTransaction createShipment(ProductTransaction shipment) {
        if (fundService.findByProductId(shipment.getProduct().getId()).getBalance() < shipment.getQuantity()){
            throw new CustomException("The number of products exceeds the balance", HttpStatus.BAD_REQUEST);
        }
        shipment.setQuantity(-Math.abs(shipment.getQuantity()));
        return create(shipment);
    }
    public ProductTransaction create(ProductTransaction productTransaction) {
        productTransaction.setTransactionDate(new Date());
        productTransaction.setUser(usersService.findLoggedInUser().get());
        return productTransactionRepository.save(productTransaction);
    }
    public ProductTransaction saveOrUpdate(ProductTransaction productTransaction) {
        productTransaction.setTransactionDate(new Date());
        productTransaction.setUser(usersService.findLoggedInUser().get());
        
        return productTransactionRepository.save(productTransaction);
    }
}

