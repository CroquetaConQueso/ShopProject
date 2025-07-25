package com.proyectotienda.controller;

import com.proyectotienda.model.Product;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SceneControllerCard{
    private Product product;

    @FXML
    private Label labelProductName;
    @FXML
    private Label labelProductPrice;
    //Establishes the setter
    public void setProduct(Product p){
        product = p;
    }
    //Gives value to the labels of the product card
    public void giveValuesCard(){
        if(product != null){
            labelProductName.setText(product.getProductName());
            labelProductPrice.setText(String.valueOf(product.getProductPrice()));
        }
    }

    public void addProductButton(ActionEvent event){
        
    }

    public void removeProductButton(ActionEvent event){

    }

}
