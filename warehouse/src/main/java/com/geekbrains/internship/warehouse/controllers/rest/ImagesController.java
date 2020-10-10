package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.entities.Image;
import com.geekbrains.internship.warehouse.services.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
@Api("Set of endpoints for CRUD operations for Image")
public class ImagesController {
    private ImageService imageService;

    @Autowired
    private ImagesController(ImageService imageService){
        this.imageService = imageService;
    }

    @GetMapping
    @ApiOperation("Returns list of all images")
    public List<Image> getAllImages() {
        return imageService.findAll();
    }

    @PostMapping
    @ApiOperation("Creates a new image")
    public Image createNewImage(Image image) {
        return imageService.saveOrUpdate(image);
    }
}
