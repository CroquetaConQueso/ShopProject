package com.proyectotienda.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.proyectotienda.app.AppContext;
import com.proyectotienda.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

//This Class is meant to control the registration, it is a simple class which displays the future values. Having a turn on/off of the passfield into a textfield
public class SceneControllerRegister implements Initializable {
        @FXML
    private AnchorPane anchorRegister;

    @FXML
    private Button buttonRegister;

    @FXML
    private CheckBox checkRegister;

    @FXML
    private Label errorNameRegister;

    @FXML
    private Label errorPasswordConfirmationRegister;

    @FXML
    private Label errorPasswordRegister;

    @FXML
    private TextField nameTextFieldRegister;

    @FXML
    private PasswordField passwordFieldConfirmationRegister;

    @FXML
    private PasswordField passwordFieldRegister;

    @FXML
    private Button returnToLogginButton;

    @FXML
    private Label titleRegister;

    public void returnToLoggin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/primary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/logginstyle.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void registerAccount(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Created User");
        alert.setContentText("Your user was created successfully");

        // If there's an error it won't allow the flow to continue
        boolean check = true;

        if (!AppContext.getUserInputHandler().getValidatedName(nameTextFieldRegister.getText())) {
            errorNameRegister.setText("The name must be between 2 and 50 characters");
            errorNameRegister.setVisible(true);
            errorNameRegister.setManaged(true);
            // There's an error therefore..
            check = false;

        } else if (!AppContext.getUserInputHandler().getValidatedPassword(passwordFieldRegister.getText())) {
            errorPasswordRegister.setText("The password must be alphanumerical and have between 7 and 50 characters");
            errorPasswordRegister.setVisible(true);
            errorPasswordRegister.setManaged(true);
            check = false;
        }

        String pass = passwordFieldRegister.getText();
        if (!pass.equals(passwordFieldConfirmationRegister.getText())) {
            errorPasswordConfirmationRegister.setText("The passwords are not equal");
            errorPasswordConfirmationRegister.setVisible(true);
            errorPasswordConfirmationRegister.setManaged(true);
            check = false;
        }

        // If any error took place, the flow won't continue
        if (!check)
            return;

        errorNameRegister.setText("");
        errorNameRegister.setVisible(false);
        errorPasswordRegister.setText("");
        errorPasswordConfirmationRegister.setText("");

        Optional<ButtonType> result = result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            User newUser = User.builder().userName(nameTextFieldRegister.getText()).userPass(pass).userFunds(0).build();
            AppContext.getUserDAO().addUser(newUser);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/primary.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/css/logginstyle.css").toExternalForm());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        } else {
            return;
        }
    }

    //If they are currently not there within here or the set they will be visible
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorNameRegister.setVisible(false);
        errorNameRegister.setManaged(false);
        errorPasswordRegister.setVisible(false);
        errorPasswordRegister.setManaged(false);
        errorPasswordConfirmationRegister.setVisible(false);
        errorPasswordConfirmationRegister.setManaged(false);

    }
}
