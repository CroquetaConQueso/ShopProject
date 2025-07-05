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
        String pName = "Introduce the name of the product:" ;
        String pType = "Introduce the type of the product: ";
        String pPrice = "Introduce the price of the product: ";


        Product product = Product.builder().productName(productInputHandler.getValidatedName(pName))
                .productType(productInputHandler.getValidatedType(pType))
                .productPrice(productInputHandler.getValidatedPrice(pPrice)).build();
        boolean check = productDAO.addProduct(product);

        if(!check){
            System.out.println("Error while trying to add a product to the database");
            return null;
        }

        return product;
    }

    public void deleteProduct(){
        String pName = "Introduce the name of the product that you wish to delete: " ;
        String name = productInputHandler.getValidatedName(pName);

        if(!productDAO.checkProduct(name)){
            System.out.println("The name of the product wasn't found");
        }else{
            productDAO.dropProduct(name);
        }
        
    }
}