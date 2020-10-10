package com.geekbrains.internship.warehouse.services;

import com.geekbrains.internship.warehouse.entities.Image;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import com.geekbrains.internship.warehouse.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
    public Image saveOrUpdate(Image image){
        Image savedImage = imageRepository.findByTitle(image.getTitle());
        return savedImage == null ? imageRepository.save(image) : savedImage;
    }
    public Image getById(Long id){
        return imageRepository.findById(id).orElseThrow(()->new CustomException("Image not found by id="+id, HttpStatus.NOT_FOUND));
    }

    public List<Image> findAll() {
        return imageRepository.findAll();
    }
}
