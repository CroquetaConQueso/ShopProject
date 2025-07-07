package com.proyectotienda.model;

import java.util.Map;

public interface CartActions {

    void addProduct(Product addP, int i);
    void removeProduct(Product removeP, int rmQ);
    float calculateTotal();
    void emptyCart();
    void showCart();
    
    Map<Product, Integer> getProducts();
}