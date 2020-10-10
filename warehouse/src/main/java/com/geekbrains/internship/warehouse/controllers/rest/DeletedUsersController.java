package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.entities.DeletedUser;
import com.geekbrains.internship.warehouse.entities.User;
import com.geekbrains.internship.warehouse.services.DeletedUsersService;
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
@RequestMapping("/api/v1/users/deleted")
@Api("Set of endpoints for CRUD operations for User")
public class DeletedUsersController {
    private DeletedUsersService deletedUsersService;

    @Autowired
    public DeletedUsersController(DeletedUsersService deletedUsersService) {
        this.deletedUsersService = deletedUsersService;
    }

    @GetMapping
    public List<DeletedUser> read() {
        return deletedUsersService.findAll();
    }
}

