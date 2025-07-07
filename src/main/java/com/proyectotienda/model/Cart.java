package com.proyectotienda.model;

import java.util.HashMap;
import java.util.Map;

public class Cart implements CartActions {
    private Map<Product, Integer> products = new HashMap<>();

    @Override
    public void addProduct(Product addP, int quantity) {
        products.merge(addP, quantity, Integer::sum);
    }

    @Override
    public void removeProduct(Product removeP, int rmQ) {
        if (products.containsKey(removeP)) {
            products.computeIfPresent(removeP, (k, v) -> v > rmQ ? v - rmQ : null);
        }
    }

    @Override
    public float calculateTotal(){
        return (float) products.entrySet().stream()
                .mapToDouble(e -> e.getKey().getProductPrice() * e.getValue())
                .sum();
    }

    @Override
    public void emptyCart() {
        products.clear();
    }

    @Override
    public void showCart(){
        if (products.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                System.out.println(entry.getKey() + " | Quantity: " + entry.getValue()+" | Total: "+entry.getKey().getProductPrice()*entry.getValue());
            }
        }
    }
    @Override
    public Map<Product, Integer> getProducts() {
        return products;
    }
}