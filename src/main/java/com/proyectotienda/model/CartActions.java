package com.proyectotienda.model;

import java.util.Map;

public interface CartActions {

    void addProduct(Product addP);
    void removeProduct(Product removeP);
    float calculateTotal();
    void emptyCart();
    void showCart();
    
    Map<Product, Integer> getProducts();
}