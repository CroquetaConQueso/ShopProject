package com.proyectotienda.model;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Cart implements CartActions {
    private final ObservableMap<Product, Integer> products = FXCollections.observableHashMap();

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
    public float calculateTotal() {
        return (float) products.entrySet().stream()
                .mapToDouble(e -> e.getKey().getProductPrice() * e.getValue())
                .sum();
    }

    @Override
    public void emptyCart() {
        products.clear();
    }

    @Override
    public String showCart() {
        if (products.isEmpty()) {
            return "The cart is empty";
        } else {
            StringBuilder total = new StringBuilder();
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                total.append(String.format("%s  -  %d - Total: %.2f\n", entry.getKey().getProductName(),
                        entry.getValue(), entry.getKey().getProductPrice() * entry.getValue()));
                // System.out.println(entry.getKey() + " | Quantity: " + entry.getValue()+" |
                // Total: "+entry.getKey().getProductPrice()*entry.getValue());
            }
            return total.toString();
        }
    }

    @Override
    public ObservableMap<Product, Integer> getProducts() {
        return products;
    }
}