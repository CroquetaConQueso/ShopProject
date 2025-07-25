package com.proyectotienda.controller;

import java.io.IOException;

import com.proyectotienda.app.AppContext;
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


//Barebones done
public class SceneControllerLogin {
    private UserDAO userDAO;

    @FXML
    public void initialize(){
        this.userDAO = AppContext.getUserDAO();
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
        errorUserNameLabel.setVisible(false);
        errorUserNameLabel.setManaged(false);
        errorUserPassLabel.setVisible(false);
        errorUserPassLabel.setManaged(false);
        
        String username = textFieldUserSc1.getText();
        String userpass = passFieldUserSc1.getText();

        
        if (!userDAO.checkUser(username, userpass)){
            alert.setTitle("Credentials Error");
            alert.setHeaderText("Error with the username/password");
            alert.setContentText("The password or the account name weren't on the database");
            alert.showAndWait();
            return;
        }
        User s = userDAO.logUserDao(username);
        
        errorUserNameLabel.setText("");
        errorUserPassLabel.setText("");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/secondary.fxml"));
        Parent root = loader.load();
        SceneControllerMenu menuController = loader.getController();
        menuController.setUser(s);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void registerAccount(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){System.out.println("Error: "+e);}
    }

}
