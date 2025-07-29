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

    // In java, whenever a class is being used as a key in a structure like a
    // HashMap or HashSet, it is fundamental to overwrite its equals and hashcode.
    // To avoid that a product of the same type is not considered of said type due
    // to its memory position, we have to rewrite the equals and hash code. By
    // default, equals matches by reference(which means that if they are the same
    // object in memory) which is not useful if we have two products with the same
    // name that are on different slots of memory. Therefore we redifine equals to
    // match the names of the products and the hasCode , allowing equal products to
    // have the same hash and be correctly found or replaced in a loop.
    @Override
    public boolean equals(Object p) {
        if (this == p)
            return true;
        if (p == null || getClass() != p.getClass())
            return false;

        Product that = (Product) p;
        return this.productName.equals(that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName);
    }

}
