package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.entities.UserAction;
import com.geekbrains.internship.warehouse.services.UserActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/actions")
@Api("Set of endpoints for CRUD operations for UserAction")
public class UserActionsController {
    @Autowired
    private UserActionService userActionService;

    @GetMapping(produces = "application/json")
    public List<UserAction> getAllActions() {
        return userActionService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns transactions by product id")
    public ResponseEntity<?> getOneProductTransactions(@PathVariable Long id) {
        return new ResponseEntity<>(userActionService.getProductTransactions(id), HttpStatus.OK);
    }

    @GetMapping(value = "/author", produces = "application/json")
    @ApiOperation("Returns transactions by author")
    public ResponseEntity<?> getAuthorTransactions(@RequestParam(name = "author") String author) {
        return new ResponseEntity<>(userActionService.getAuthorTransactions(author), HttpStatus.OK);
    }

    @GetMapping(value = "/data", produces = "application/json")
    @ApiOperation("Returns transactions by data")
    public ResponseEntity<?> getDataTransactions(@RequestParam ("data")
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data) {
        return new ResponseEntity<>(userActionService.getDateTransactions(data), HttpStatus.OK);
    }
}
