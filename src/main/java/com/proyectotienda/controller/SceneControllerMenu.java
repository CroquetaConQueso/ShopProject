package com.proyectotienda.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.proyectotienda.app.AppContext;
import com.proyectotienda.model.Product;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class SceneControllerMenu implements Initializable{
    @FXML
    private ListView<String> listViewProducts;
    @FXML
    private Label labelTitle;
    @FXML
    private TextArea dataDump;

    String[] listValues = {"Food","Clothing","Electronics"};
    String currentChoice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewProducts.getItems().addAll(listValues);
        listViewProducts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentChoice = listViewProducts.getSelectionModel().getSelectedItem();
                labelTitle.setText(currentChoice);
            }
            
        });
    }

    public void logged(ActionEvent event) throws IOException{
        ArrayList<Product> listilla = new ArrayList<>();
        int countFood=0;
        int countClothing=0;
        int countElectronics=0;
        int column = 0;
        int row = 0;
        int totalTypes=3;

        for (Product product : listilla) {
            if(product.getProductType().equals("Food")){
                countFood+=1;
            }else if(product.getProductType().equals("Clothing")){
                countClothing+=1;
            }else if(product.getProductType().equals("Electronics")){
                countElectronics+=1;
            }

        }
        
        
    }
}
