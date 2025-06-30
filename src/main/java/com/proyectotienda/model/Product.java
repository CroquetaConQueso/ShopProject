package com.proyectotienda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Product {
    private String productName;
    private String productType;
    private float productPrice;
    private int productQuantity;
}
