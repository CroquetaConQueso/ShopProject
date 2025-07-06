package com.proyectotienda.controller;

//It will control the flow of the creation and the connectivity of all the other Product classes
import com.proyectotienda.model.Product;
import com.proyectotienda.model.User;
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

    public void checkTypesProducts(){
        String typePrompt = "List of the possible types of products: "; 
        String tName = productInputHandler.getValidatedType(typePrompt);
        productDAO.listingTypesProducts(tName);
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

    public void userBuysProduct(User loggedUser){
        String promptBuy = "Introduce the name of the product that you want to buy: ";
        String nPro = productInputHandler.getValidatedName(promptBuy);

        System.out.println("How many do you want to buy?");
        int qPro = productInputHandler.getValidatedQuantity();

        Product p = productDAO.buyProduct(nPro,qPro);
        if (loggedUser.getUserFunds() >= (p.getProductPrice()*p.getProductQuantity())) {
            loggedUser.setUserFunds(loggedUser.getUserFunds()-(p.getProductPrice()*p.getProductQuantity()));
            loggedUser.getUserCart().addProduct(p);
            
            System.out.println("You bought "+p.getProductQuantity()+ " units of " + p.getProductName() + "for the price of "
                            + p.getProductPrice() + "\n\nNow you have " + loggedUser.getUserFunds() + "e left");
            
        }
    }
                        
}