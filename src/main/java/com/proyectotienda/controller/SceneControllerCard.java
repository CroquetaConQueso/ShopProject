package com.proyectotienda.controller;

import com.proyectotienda.model.Cart;
import com.proyectotienda.model.Product;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SceneControllerCard{
    private Product product;
    private Cart userCart;
    @FXML
    private Label labelProductName;
    @FXML
    private Label labelProductPrice;
    //Establishes the setter
    public void setProduct(Product p){
        this.product = p;
    }
    public void setUserCart(Cart c){
        this.userCart = c;
    }
    //Gives value to the labels of the product card
    public void giveValuesCard(){
        if(product != null){
            labelProductName.setText(product.getProductName());
            labelProductPrice.setText(String.valueOf(product.getProductPrice()));
        }
    }

    public void addProductButton(ActionEvent event){
        userCart.addProduct(product, 1);
    }

    public void removeProductButton(ActionEvent event){
        userCart.removeProduct(product, 1);
    }

}
