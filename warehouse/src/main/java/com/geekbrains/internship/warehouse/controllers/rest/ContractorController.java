package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.entities.Contractor;
import com.geekbrains.internship.warehouse.entities.Product;
import com.geekbrains.internship.warehouse.entities.dtos.ErrorDto;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import com.geekbrains.internship.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.internship.warehouse.services.ContractorService;
import com.geekbrains.internship.warehouse.services.ProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/contractors")
@Api("Set of endpoints for CRUD operations for Contractor")
public class ContractorController {
    private ContractorService contractorService;
    private ProductsService productsService;
    @Autowired
    public ContractorController(ContractorService contractorService, ProductsService productsService){
        this.contractorService = contractorService;
        this.productsService = productsService;
    }


    @GetMapping
    @ApiOperation("Returns list of all contractors")
    public List<Contractor> getAllContractors() {
        return contractorService.findAll();
    }

    @GetMapping("/providers/{productId}")
    @ApiOperation("Returns list of providers by product.id")
    public Set<Contractor> getProvidersByProduct(@PathVariable Long productId) {
        return contractorService.getProvidersByProduct(productsService.findById(productId));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new contractor")
    public Contractor createContractor(@RequestBody Contractor contractor) {
        return contractorService.saveOrUpdate(contractor);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one contractor by id")
    public List<Contractor> deleteOneProducts(@PathVariable Long id) {
        contractorService.deleteById(id);
        return contractorService.findAll();
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing contractor")
    public List<Contractor> modifyCategory(@RequestBody Contractor contractor) {
        if (contractor.getId() == null || !contractorService.existsById(contractor.getId())) {
            throw new ProductNotFoundException("Product not found, id: " + contractor.getId());
        }
        contractorService.saveOrUpdate(contractor);
        return contractorService.findAll();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(CustomException exception){
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()),exception.getStatus());
    }
}
