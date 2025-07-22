package com.proyectotienda.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.proyectotienda.app.AppContext;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class SceneControllerMenu implements Initializable{
    @FXML
    private ListView<String> listViewProducts;
    @FXML
    private Label labelTitle;

    List<Product> productList = AppContext.geProductDAO().showProducts();
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


    }
}
