package com.geekbrains.internship.warehouse.entities.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
