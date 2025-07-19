package com.proyectotienda.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Product {
    private int productId;
    private String productName;
    private String productType;
    private float productPrice;
    private int productQuantity;


@Override
public boolean equals(Object  p){
    if(this == p) return true;
    if(p == null || getClass() != p.getClass()) return false;

    Product that = (Product) p;
    return this.productName.equals(that.productName);
}

@Override
public int hashCode(){
    return Objects.hash(productName);
}

}
