package com.proyectotienda.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.proyectotienda.app.AppContext;
import com.proyectotienda.model.Cart;
import com.proyectotienda.model.User;
import com.proyectotienda.repository.UserDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.stage.Stage;

//Barebones done
public class SceneControllerLogin implements Initializable{
    private UserDAO userDAO;

    @FXML
    private AnchorPane anchorPaneLoggin;

    @FXML
    private Label labelPassword;

    @FXML
    private Label labelPromptRegister;

    @FXML
    private Label labelUserName;

    @FXML
    private PasswordField passFieldUserSc1;

    @FXML
    private Button registerBottonSc1;

    @FXML
    private TextField textFieldUserSc1;

    @FXML
    private TextField passTextUserSc1;

    @FXML
    private Label welcomingLabel;

    @FXML
    private Pane titlePane;

    @FXML
    private CheckBox showPassCheckLoggin;

    // Method that setups the loggin of the suer
    public void loggin(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.ERROR);
        String username = textFieldUserSc1.getText();
        String userpass = passFieldUserSc1.getText();

        if (!userDAO.checkUser(username, userpass)) {
            alert.setTitle("Credentials Error");
            alert.setHeaderText("Error with the username/password");
            alert.setContentText("The password or the account name weren't on the database");
            alert.showAndWait();
            return;
        }
        User s = userDAO.logUserDao(username);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/secondary.fxml"));
        Parent root = loader.load();
        SceneControllerMenu menuController = loader.getController();
        Cart userCart = new Cart();
        // passes the user to the main menu
        s.setUserCart(userCart);
        menuController.setUser(s);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // If the user doesn't have an user they can log
    public void registerAccount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/registerstyle.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userDAO = AppContext.getUserDAO();
        passTextUserSc1.setVisible(false);
        passTextUserSc1.setManaged(false);
        passFieldUserSc1.textProperty().addListener((obs,oldText,newText) -> passTextUserSc1.setText(newText));
        passTextUserSc1.textProperty().addListener((obs,oldText,newText) -> passFieldUserSc1.setText(newText));

        showPassCheckLoggin.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
        if (isSelected) {
            passTextUserSc1.setText(passFieldUserSc1.getText());
            passTextUserSc1.setVisible(true);
            passTextUserSc1.setManaged(true);
            passFieldUserSc1.setVisible(false);
            passFieldUserSc1.setManaged(false);
        } else {
            passFieldUserSc1.setText(passTextUserSc1.getText());
            passFieldUserSc1.setVisible(true);
            passFieldUserSc1.setManaged(true);
            passTextUserSc1.setVisible(false);
            passTextUserSc1.setManaged(false);
        }
    });
    }

}
