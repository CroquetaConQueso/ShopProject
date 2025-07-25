package com.proyectotienda.controller;

import java.io.IOException;
import java.util.Optional;

import com.proyectotienda.app.AppContext;
import com.proyectotienda.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SceneControllerRegister {
    @FXML
    private Button returnToLoginButton;
    @FXML
    private Button buttonRegister;
    @FXML
    private TextField nameTextFieldRegister;
    @FXML
    private PasswordField passwordFieldRegister;
    @FXML
    private PasswordField passwordFieldConfirmationRegister;
    @FXML
    private Label errorNameRegister;
    @FXML
    private Label errorPasswordRegister;
    @FXML
    private Label errorPasswordConfirmationRegister;

    public void returnToLoggin(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/primary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void registerAccount(ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Created User");
        alert.setContentText("Your user was created successfully");

        
        //If there's an error it won't allow the flow to continue
        boolean check = true;

        errorNameRegister.setVisible(false);
        errorNameRegister.setManaged(false);
        errorPasswordRegister.setVisible(false);
        errorPasswordRegister.setManaged(false);
        errorPasswordConfirmationRegister.setVisible(false);
        errorPasswordConfirmationRegister.setManaged(false);

        if(!AppContext.getUserInputHandler().getValidatedName(nameTextFieldRegister.getText())){
            errorNameRegister.setText("The name must be between 2 and 50 characters");
            errorNameRegister.setVisible(true);
            errorNameRegister.setManaged(true);
            //There's an error therefore..
            check=false;

        }else if(!AppContext.getUserInputHandler().getValidatedPassword(passwordFieldRegister.getText())){
            errorPasswordRegister.setText("The password must be alphanumerical and have between 7 and 50 characters");
            errorPasswordRegister.setVisible(true);
            errorPasswordRegister.setManaged(true);
            check=false;
        }

        String pass = passwordFieldRegister.getText();
        if(!pass.equals(passwordFieldConfirmationRegister.getText())){
            errorPasswordConfirmationRegister.setText("The passwords are not equal");
            errorPasswordConfirmationRegister.setVisible(true);
            errorPasswordConfirmationRegister.setManaged(true);
            check=false;
        }
        
        if(!check) return;

        errorNameRegister.setText("");
        errorNameRegister.setVisible(false);
        errorPasswordRegister.setText("");
        errorPasswordConfirmationRegister.setText("");

        Optional<ButtonType> result = result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            User newUser = User.builder().userName(nameTextFieldRegister.getText()).userPass(pass).userFunds(0).build();
            AppContext.getUserDAO().addUser(newUser);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/primary.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        }else{return;}
    }
}
