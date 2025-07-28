package com.proyectotienda.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.proyectotienda.app.AppContext;
import com.proyectotienda.model.Product;
import com.proyectotienda.model.User;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneControllerMenu implements Initializable {
    private User loggedUser;
    @FXML
    private Label labelShowCart;
    @FXML
    private Button accountButton;
    @FXML
    private Button buttonCloseCart;
    @FXML
    private Button buttonShowCart;
    @FXML
    private ListView<String> listViewProducts;
    @FXML
    private Label labelTitle;
    @FXML
    private TextArea dataDump;
    @FXML
    private GridPane gridProducts;
    @FXML
    private Pane slideMenu;
    @FXML
    private Pane labelTitles;
    @FXML
    private Button buyButton;

    String[] listValues = { "Food", "Clothing", "Electronics" };
    String currentChoice;

    public void setUser(User user){
        this.loggedUser = user;

        labelShowCart.setText(loggedUser.getUserCart().showCart());

        loggedUser.getUserCart().getProducts().addListener((MapChangeListener<Product,Integer>) change -> labelShowCart.setText(loggedUser.getUserCart().showCart()));
    }
    // Loads the values of the ListView and gives them each type, which I have
    // already preestablished on the String Array
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewProducts.getItems().addAll(listValues);
        slideMenu.setTranslateX(200);
        listViewProducts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentChoice = listViewProducts.getSelectionModel().getSelectedItem();
                labelTitle.setText(currentChoice);
                if(newValue != null){
                    try{
                        logged();
                    }catch(IOException e){System.out.println("Error: "+e);}
                }
            }

        });
        
        buttonShowCart.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slideMenu);

            slide.setToX(200);
            slide.play();

            slideMenu.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)->{
                buttonShowCart.setVisible(false);
                buttonCloseCart.setVisible(true);
            });
        });

        buttonCloseCart.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slideMenu);

            slide.setToX(0);
            slide.play();

            slideMenu.setTranslateX(200);

            slide.setOnFinished((ActionEvent e) ->{
                buttonShowCart.setVisible(true);
                buttonCloseCart.setVisible(false);
            });
        });

    }

    // Method dedicated to take the name from the listview and then load each
    // product card onto the grid
    //This Method doesn't have a parameter of ActionEvent because it will be called by a Listener
    public void logged() throws IOException {
        ArrayList<Product> productsType = new ArrayList<>();
        int rows = 0;
        int columns = 0;
        int maxColumns = 3;

        //We clear what was done previously so we can reuse it later
        productsType.clear();
        gridProducts.getChildren().clear();

        String typeName = listViewProducts.getSelectionModel().getSelectedItem();

        productsType = AppContext.geProductDAO().listingTypesProducts(typeName);

        for (Product product : productsType) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cardProduct.fxml"));
                Parent card = loader.load();

                SceneControllerCard controller = loader.getController();
                controller.setProduct(product);
                controller.setUserCart(loggedUser.getUserCart());
                controller.giveValuesCard();
                
                //0 is a value, therefore the first one will be added in 0 / 0
                gridProducts.add(card, columns, rows);

                columns+=1;
                if(columns == maxColumns){
                    columns = 0;
                    rows+=1;
                }
            
            }catch(IOException e){System.out.println("Error: "+e);}
        }
    }

    public void accountData(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/accountDetails.fxml"));
            Parent root = loader.load();
            SceneControllerAccountDetails controllerAccountDetails = loader.getController();
            controllerAccountDetails.setUser(loggedUser);
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }catch(IOException e){System.out.println("Error: "+ e);}
    }

    public void disconnect(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/primary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/logginstyle.css").toExternalForm());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){System.out.println(e);}
    }

    public void showCart(ActionEvent event){
        labelShowCart.setText(loggedUser.getUserCart().showCart());
    }

    public void buyingCart(ActionEvent event){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);

        alerta.setTitle("Confirmation");
        alerta.setHeaderText("Buying Cart");
        alerta.setContentText("Do you want to confirm your transaction?");
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        
        alerta.getButtonTypes().setAll(yes,no);

        Optional<ButtonType> choice = alerta.showAndWait();

        if(choice.isPresent() && choice.get() == yes){
            System.out.println(loggedUser.buyCart());
        }else{
            return;
        }
    }

}
