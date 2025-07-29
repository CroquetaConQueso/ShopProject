package com.proyectotienda.controller;

import java.io.IOException;

import com.proyectotienda.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SceneControllerAccountDetails {
    private User loggedUser;
    @FXML
    private TextField textpassfieldAccountDetails;
    @FXML
    private Button transferButtonAccountDetails;
    @FXML
    private CheckBox showpassAccountDetails;
    @FXML
    private Button returnToMenuButton;
    @FXML
    private TextField namefieldAccountDetails;
    @FXML
    private TextField fundfieldAccountDetails;
    @FXML
    private PasswordField passfieldAccountDetails;

    public void setUser(User user){
        this.loggedUser = user;
        showValues();
    }

    public void returnToMenu(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/secondary.fxml"));
            Parent root = loader.load();
            SceneControllerMenu controller = loader.getController();
            controller.setUser(loggedUser);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){System.out.println(e);}
    }

    //Displays the values of the account right away
    public void showValues(){
        textpassfieldAccountDetails.setVisible(false);
        textpassfieldAccountDetails.setManaged(false);
        namefieldAccountDetails.setText(loggedUser.getUserName());
        passfieldAccountDetails.setText(loggedUser.getUserPass());
        fundfieldAccountDetails.setText(String.valueOf(loggedUser.getUserFunds()));
        textpassfieldAccountDetails.setText(loggedUser.getUserPass());
    }
    //Allows swaps between the modes so you are able to see the pass
    public void showPass(ActionEvent event){
        if(showpassAccountDetails.isSelected()){
            passfieldAccountDetails.setVisible(false);
            passfieldAccountDetails.setManaged(false);
            textpassfieldAccountDetails.setVisible(true);
            textpassfieldAccountDetails.setManaged(true);
        }else{
            
            passfieldAccountDetails.setVisible(true);
            passfieldAccountDetails.setManaged(true);
            textpassfieldAccountDetails.setVisible(false);
            textpassfieldAccountDetails.setManaged(false);
        }
    }
    //A simple method in which funds are added into the account
    public void addFunds(ActionEvent event){
        float funds = loggedUser.getUserFunds();
        loggedUser.setUserFunds(funds+=100);
    }
}
