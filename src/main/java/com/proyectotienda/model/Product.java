package com.proyectotienda.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private String userName;
    private String userType;
    private int quantity;
}
