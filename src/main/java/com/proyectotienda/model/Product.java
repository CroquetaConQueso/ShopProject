package com.proyectotienda.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private int productId;
    private String productName;
    private String productType;
    private float productPrice;
    private int productQuantity;
}
