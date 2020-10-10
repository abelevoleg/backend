package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.entities.Unit;
import com.geekbrains.internship.warehouse.entities.dtos.ErrorDto;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import com.geekbrains.internship.warehouse.services.UnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/units")
@Api("Set of endpoints for CRUD operations for Unit")
public class UnitsController {
    @Autowired
    private UnitService unitService;

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all units")
    public List<Unit> getAllUnits() {
        return unitService.findAll();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new unit")
    public List<Unit> createUnit(@RequestBody Unit unit) {
        unitService.saveOrUpdate(unit);
        return unitService.findAll();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one unit by id")
    public List<Unit> deleteOneProducts(@PathVariable Long id) {
        unitService.deleteById(id);
        return unitService.findAll();
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing unit")
    public List<Unit> modifyUnit(@RequestBody Unit unit) {
        if (unit.getId() == null || !unitService.existsById(unit.getId())) {
            throw new CustomException("Unit not found, id: " + unit.getId(), HttpStatus.NOT_FOUND);
        }
        unitService.saveOrUpdate(unit);
        return unitService.findAll();
    }
    @ExceptionHandler
    public ResponseEntity<?> handleException(CustomException exception){
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()),exception.getStatus());
    }
}
