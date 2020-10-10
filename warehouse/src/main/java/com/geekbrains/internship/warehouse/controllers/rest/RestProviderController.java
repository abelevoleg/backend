package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.entities.Provider;
import com.geekbrains.internship.warehouse.services.ProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/providers")
@Api("Set of endpoints for CRUD operations for Providers")
public class RestProviderController {

        private ProviderService providerService;

        @Autowired
        public RestProviderController(ProviderService providerService) {
            this.providerService = providerService;
        }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation("Creates a new provider")
    public Provider create(@RequestBody Provider provider) {
        return providerService.create(provider);
    }

    @GetMapping
    public ResponseEntity<List<Provider>> read() {
        final List<Provider> providers = providerService.readAll();

        return providers != null &&  !providers.isEmpty()
                ? new ResponseEntity<>(providers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns one provider by id")
    public ResponseEntity<?> read(@PathVariable Long id) {
        final Provider provider = providerService.read(id);
        return provider != null
                ? new ResponseEntity<>(provider, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Provider provider) {
        final boolean updated = providerService.update(provider, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = providerService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
