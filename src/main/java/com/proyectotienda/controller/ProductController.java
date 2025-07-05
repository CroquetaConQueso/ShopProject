package com.proyectotienda.controller;

//It will control the flow of the creation and the connectivity of all the other Product classes
import com.proyectotienda.model.Product;
import com.proyectotienda.repository.ProductDAO;

public class ProductController {
    private final ProductDAO productDAO;
    private final ProductInputHandler productInputHandler;

    public ProductController(ProductDAO productDAO, ProductInputHandler productInputHandler) {
        this.productDAO = productDAO;
        this.productInputHandler = productInputHandler;
    }

    public Product createProduct() {
        Product product = Product.builder().productName(productInputHandler.getValidatedName())
                .productType(productInputHandler.getValidatedType())
                .productPrice(productInputHandler.getValidatedPrice()).build();
        boolean check = productDAO.addProduct(product);

        if(!check){
            System.out.println("Error while trying to add a product to the database");
            return null;
        }

        return product;
    }
}