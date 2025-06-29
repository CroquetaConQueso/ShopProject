package com.proyectotienda.model;

import java.util.Map;

public interface CartActions {

    void addProduct(Product addP);
    void removeProduct(Product removeP);
    float calculateTotal();
    void emptyCart();

    Map<Product, Integer> getProducts();
}