package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.entities.Role;
import com.geekbrains.internship.warehouse.services.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@Api("Set of endpoints for CRUD operations for Roles")
public class RolesController {
    private RoleService roleService;

    @Autowired
    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all roles")
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }
}

