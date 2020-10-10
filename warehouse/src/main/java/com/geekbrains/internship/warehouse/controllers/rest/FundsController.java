package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.entities.Fund;
import com.geekbrains.internship.warehouse.entities.Product;
import com.geekbrains.internship.warehouse.entities.dtos.ErrorDto;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import com.geekbrains.internship.warehouse.services.FundService;
import com.geekbrains.internship.warehouse.services.ProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/funds")
@Api("Set of endpoints for CRUD operations for Fund")
public class FundsController {
    @Autowired
    private FundService fundService;

    @Autowired
    private ProductsService productsService;


    @ApiOperation("Returns list of all funds")
    @GetMapping
    public List<Fund> getAllFunds() {
        return fundService.findAll();
    }

    @ApiOperation("Returns fund by Product.id")
    @GetMapping("/product/{var}")
    public Fund getFundByProductId(@PathVariable String var) {
        Product product;
        try{
            product =  productsService.findById(Long.parseLong(var));
        } catch (NumberFormatException e){
            product =  productsService.findByTitle(var);
        }
        if (product == null) throw new CustomException(String.format("Can't find product by \"%s\"",var), HttpStatus.NOT_FOUND);
        return fundService.findByProductId(product.getId());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(CustomException exception){
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()),exception.getStatus());
    }
}
