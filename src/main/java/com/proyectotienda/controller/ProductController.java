package com.proyectotienda.controller;

import java.util.Scanner;

//It will control the flow of the creation and the connectivity of all the other Product classes
import com.proyectotienda.model.Product;
import com.proyectotienda.model.User;
import com.proyectotienda.repository.ProductDAO;
import com.proyectotienda.repository.CartDAO;

public class ProductController {
    private final ProductDAO productDAO;
    private final ProductInputHandler productInputHandler;

    public ProductController(ProductDAO productDAO, ProductInputHandler productInputHandler) {
        this.productDAO = productDAO;
        this.productInputHandler = productInputHandler;
    }

    public Product createProductAdmin() {
        String pName = "Introduce the name of the product:";
        String pType = "Introduce the type of the product: ";
        String pPrice = "Introduce the price of the product: ";

        Product product = Product.builder().productName(productInputHandler.getValidatedName(pName))
                .productType(productInputHandler.getValidatedType(pType))
                .productPrice(productInputHandler.getValidatedPrice(pPrice)).build();
        boolean check = productDAO.addProduct(product);

        if (!check) {
            System.out.println("Error while trying to add a product to the database");
            return null;
        }

        return product;
    }

    public void checkTypesProducts() {
        String typePrompt = "List of the possible types of products: ";
        String tName = productInputHandler.getValidatedType(typePrompt);
        productDAO.listingTypesProducts(tName);
    }

    public void deleteProductAdmin() {
        String pName = "Introduce the name of the product that you wish to delete: ";
        String name = productInputHandler.getValidatedName(pName);

        if (!productDAO.checkProduct(name)) {
            System.out.println("The name of the product wasn't found");
        } else {
            productDAO.dropProduct(name);
        }

    }

    public Product userBuysProduct(User loggedUser) {
        String promptBuy = "Introduce the name of the product that you want to buy: ";
        String nPro = productInputHandler.getValidatedName(promptBuy);
        Scanner input = new Scanner(System.in);

        System.out.println("How many do you want to buy?");
        int qPro = input.nextInt();
        input.close();

        Product p = productDAO.buyProduct(nPro, qPro);
        if (loggedUser.getUserFunds() >= (p.getProductPrice() * p.getProductQuantity())) {
            loggedUser.setUserFunds(loggedUser.getUserFunds() - (p.getProductPrice() * p.getProductQuantity()));
            // Had to add a new value to have it be passed to the add methode
            loggedUser.getUserCart().addProduct(p, qPro);

            System.out.println("You bought " + loggedUser.getUserCart().getProducts().get(p) + " units of "
                    + p.getProductName() + "for the price of "
                    + p.getProductPrice() + "\n\nNow you have " + loggedUser.getUserFunds() + "e left");
            
            return p;
        } else {
            System.out.println("You do not have enough funds to buy what you want!");
            return null;
        }
    }

    // User RemovesProducts needs to be overhauled and most likely the 1.0.0 version
    // will be done
    public void userRemovesProduct(User loggedUser) {
        if (loggedUser.getUserCart().getProducts().isEmpty()) {
            System.out.println("You must get a product first before deleting it");
        } else {
            String prompt = "Name of the product that you wish to remove: ";
            String pName = productInputHandler.getValidatedName(prompt);

            Product toRemove = loggedUser.getUserCart().getProducts().keySet().stream()
                    .filter(p -> p.getProductName().equalsIgnoreCase(pName)).findFirst().orElse(null);
            if(toRemove != null){
                int quantityRemove = productInputHandler.getValidatedQuantity();
                loggedUser.getUserCart().removeProduct(toRemove, quantityRemove );
            }else{
                System.out.println("The product was not found in your cart");
            }
        }
    }

    public void showAllProducts() {
        System.out.println("List of all of the products: \n");
        productDAO.showProducts();
    }

}