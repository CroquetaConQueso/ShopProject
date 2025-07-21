package com.proyectotienda.controller;

import java.io.IOException;

import com.proyectotienda.model.User;
import com.proyectotienda.repository.UserDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SceneControllerLogin {
    private UserController userController;
    private User s;
    private UserDAO userDAO;
    public void setUserController(UserController userController){
        this.userController = userController;
    }

    @FXML
    private Label errorUserNameLabel;

    @FXML
    private Label errorUserPassLabel;

    @FXML
    private TextField textFieldUserSc1;

    @FXML
    private PasswordField passFieldUserSc1;

    public void loggin(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.ERROR);
        String username = textFieldUserSc1.getText();
        String userpass = passFieldUserSc1.getText();

        if (!UserInputHandler.getValidatedName1(username)){
            errorUserNameLabel.setText("The username must be 2-50 characters long");
            return ;
        }        
        if (!UserInputHandler.getValidatedPassword1(userpass)){
            errorUserPassLabel.setText("The password must be alphanumerical and have at least 7 characters");
            return ;
        }
        if (!userDAO.checkUser(username, userpass)){
            alert.setTitle("Credentials Error");
            alert.setHeaderText("Error with the username/password");
            alert.setContentText("The password or the account name weren't on the database");
        }
        userDAO.logUserDao(username);
        errorUserNameLabel.setText("");
        errorUserPassLabel.setText("");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/secondary.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
