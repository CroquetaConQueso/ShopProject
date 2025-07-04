package com.proyectotienda.controller;

import java.util.Scanner;

public class ProductInputHandler {
    private final Scanner input;
    private String[] productList = {
            "Fruits", "Fish", "Vegetables", "Meat",
            "Dairy", "Bakery", "Snacks", "Beverages",
            "Frozen", "Canned", "Grains", "Spices"
    };

    public ProductInputHandler(Scanner input) {
        this.input = input;
    }

    public String getValidatedName() {
        System.out.print("Introduce the name of the product");
        String name = input.nextLine();

        if (name.matches("^[a-zA-Z0-9]+")) {
            if (name.length() < 3) {
                System.out.println("The product needs to have more than or 3 characters");
                return getValidatedName();
            } else if (name.length() > 100) {
                System.out.println("The name of the product can't be longer than 100 characters");
                return getValidatedName();
            } else {
                return name;
            }
        } else {
            System.out.println("The name can only contain alphanumerical characters");
            return getValidatedName();
        }
    }

    public String getValidatedType() {
        System.out.println("Lists of types products: ");
        for (int i = 0; i < productList.length; i++) {
            System.out.println(productList[i]);
        }

        System.out.print("Introduce the type of the product: ");
        String type = input.nextLine();
        type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();

        for (int i = 0; i < productList.length; i++) {
            String value = productList[i];
            if (value.equals(type)) {
                System.out.println("The type was added successfully");
                return type;
            }
        }

        System.out.println("The type must be one of the allowed types");
        return getValidatedType();

    }

    public float getValidatedPrice() {
        System.out.println("Introduce the price: ");
        String price = input.nextLine().replace(',', '.');

        if (!price.matches("^\\d+(\\.\\d{1,2})?$")) {
            System.out.println("Invalid funds format. Only numbers with up to two decimal places are allowed.");
            return getValidatedPrice();
        }
        float convertedPrice = Float.parseFloat(price);

        if (convertedPrice <= 0) {
            System.out.println("The product can't be free");
            return getValidatedPrice();
        }

        return convertedPrice;
    }
}