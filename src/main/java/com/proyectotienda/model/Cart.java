package com.proyectotienda.model;

import java.util.HashMap;
import java.util.Map;

public class Cart implements CartActions {
    private Map<Product, Integer> Products = new HashMap<>();

    @Override
    public void addProduct(Product addP) {
        Products.merge(addP, 1, Integer::sum);
    }

    @Override
    public void removeProduct(Product removeP) {
        if (Products.containsKey(removeP)) {
            Products.computeIfPresent(removeP, (k, v) -> v > 1 ? v - 1 : null);
        }
    }

    @Override
    public float calculateTotal(){
        return (float) Products.entrySet().stream()
                .mapToDouble(e -> e.getKey().getProductPrice() * e.getValue())
                .sum();
    }

    @Override
    public void emptyCart() {
        Products.clear();
    }

    @Override
    public Map<Product, Integer> getProducts() {
        return Products;
    }
}