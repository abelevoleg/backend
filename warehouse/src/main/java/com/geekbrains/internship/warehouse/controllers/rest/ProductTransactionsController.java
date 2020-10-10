package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.entities.Product;
import com.geekbrains.internship.warehouse.entities.ProductTransaction;
import com.geekbrains.internship.warehouse.entities.UserAction;
import com.geekbrains.internship.warehouse.entities.dtos.ErrorDto;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import com.geekbrains.internship.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.internship.warehouse.services.UserActionService;
import com.geekbrains.internship.warehouse.services.ProductTransactionService;
import com.geekbrains.internship.warehouse.services.ProductsService;
import com.geekbrains.internship.warehouse.services.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/transactions")
@Api("Set of endpoints for CRUD operations for ProductTransaction")
public class ProductTransactionsController {
    private ProductTransactionService productTransactionService;
    private ProductsService productsService;
    private UsersService usersService;
    private UserActionService userActionService;

    @Autowired
    public ProductTransactionsController(ProductTransactionService productTransactionService, ProductsService productsService,
                                         UsersService usersService, UserActionService userActionService) {
        this.productTransactionService = productTransactionService;
        this.productsService = productsService;
        this.usersService = usersService;
        this.userActionService = userActionService;
    }

    @ApiOperation("Returns list of all product transactions")
    @GetMapping
    public List<ProductTransaction> getAllProductTransactions() {
        return productTransactionService.getAllTransactions();
    }

    @GetMapping("/supply")
    @ApiOperation("Returns list of all product supply transactions")
    public List<ProductTransaction> getProductSupplyTransactions() {
        return productTransactionService.getSupplyTransactions();
    }

    @GetMapping("/shipment")
    @ApiOperation("Returns list of all product shipment transactions")
    public List<ProductTransaction> getProductShipmentTransactions() {
        return productTransactionService.getShipmentTransactions();
    }

    @GetMapping("/product/{id}")
    @ApiOperation("Returns list of all product transactions by product.id")
    public List<ProductTransaction> getTransactionsByProduct(@PathVariable Long id) {
        Product product = productsService.findById(id);
        if (product == null) throw new CustomException(String.format("Can't find product by id=%d",id), HttpStatus.NOT_FOUND);
        return productTransactionService.getTransactionsByProduct(product);
    }

    @PostMapping("/supply")
    @ApiOperation("Creates a new supply transaction")
    public List<ProductTransaction> createSupplyTransaction(@RequestBody ProductTransaction productTransaction) {
        productTransactionService.createSupply(productTransaction);

        UserAction userAction = new UserAction(null, "SUPPLY", productTransaction.getProduct().getId(),
                productTransaction.getProduct().getTitle(),
                usersService.currentUserFullname());
        userActionService.saveOrUpdate(userAction);
        return productTransactionService.getSupplyTransactions();
    }


    @PostMapping("/shipment")
    @ApiOperation("Creates a new shipment transaction")
    public List<ProductTransaction> createShipmentTransaction(@RequestBody ProductTransaction productTransaction) {
        productTransactionService.createShipment(productTransaction);

        UserAction userAction = new UserAction(null, "SHIPMENT", productTransaction.getProduct().getId(),
                productTransaction.getProduct().getTitle(),
                usersService.currentUserFullname());
        userActionService.saveOrUpdate(userAction);
        return productTransactionService.getShipmentTransactions();
    }

    @PutMapping("/shipment")
    @ApiOperation("Edit a shipment transaction")
    public List<ProductTransaction> editShipmentTransaction(@RequestBody ProductTransaction productTransaction) {
        productTransactionService.saveOrUpdate(productTransaction);
        return productTransactionService.getShipmentTransactions();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(CustomException exception){
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()),exception.getStatus());
    }
}

