package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.services.DataProductHistoryService;
import com.geekbrains.internship.warehouse.services.ProductsService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operations")
@Api("Operations for Products")
public class WarehouseOperationController {
    private ProductsService productsService;
    private DataProductHistoryService dataProductHistoryService;
/*
    @Autowired
    public RestWarehouseOperationController(ProductsService productsService, DataProductHistoryService dataProductHistoryService) {
        this.productsService = productsService;
        this.dataProductHistoryService = dataProductHistoryService;
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns all history")
    public List<DataProductHistory> getAllDataHistory() {
        return dataProductHistoryService.getAllHistory();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns products history by id")
    public ResponseEntity<?> getProductsHistory(@PathVariable Long id) {
        if (!dataProductHistoryService.existsById(id)) {
            return new ResponseEntity<>("Product not found, id: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dataProductHistoryService.getProductHistory(id), HttpStatus.OK);
    }

    @PutMapping(value = "/receipts", consumes = "application/json", produces = "application/json")
    @ApiOperation("receipts products")
    public ResponseEntity<?> receiptsProduct(@RequestBody Product productReceipts) {
        if (productReceipts.getId() == null || !productsService.existsById(productReceipts.getId())) {
            return new ResponseEntity<>("Product not found, id: " + productReceipts.getId(), HttpStatus.NOT_FOUND);
        }
        if (productReceipts.getQuantity() < 0) {
            return new ResponseEntity<>("Product's quantity can not be negative", HttpStatus.BAD_REQUEST);
        }
        Product product = productsService.findById(productReceipts.getId());
        product.setQuantity(product.getQuantity() + productReceipts.getQuantity());

        DataProductHistory dataProductHistory = new DataProductHistory(null, product.getId(), productReceipts.getQuantity());
        dataProductHistoryService.saveOrUpdate(dataProductHistory);

        return new ResponseEntity<>(productsService.saveOrUpdate(product), HttpStatus.OK);
    }

    @PutMapping(value = "/shipment", consumes = "application/json", produces = "application/json")
    @ApiOperation("shipment products")
    public ResponseEntity<?> shipmentProduct(@RequestBody Product productShipment) {
        if (productShipment.getId() == null || !productsService.existsById(productShipment.getId())) {
            return new ResponseEntity<>("Product not found, id: " + productShipment.getId(), HttpStatus.NOT_FOUND);
        }
        if (productShipment.getQuantity() < 0) {
            return new ResponseEntity<>("Product's quantity can not be negative", HttpStatus.BAD_REQUEST);
        }
        Product product = productsService.findById(productShipment.getId());
        if ((product.getQuantity() - productShipment.getQuantity()) < 0){
            return new ResponseEntity<>("Product's balance can not be negative", HttpStatus.BAD_REQUEST);
        }
        product.setQuantity(product.getQuantity() - productShipment.getQuantity());

        DataProductHistory dataProductHistory = new DataProductHistory(null, product.getId(), -productShipment.getQuantity());
        dataProductHistoryService.saveOrUpdate(dataProductHistory);
        
        return new ResponseEntity<>(productsService.saveOrUpdate(product), HttpStatus.OK);
    }*/
}