package com.proyectotienda;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.proyectotienda.app.AppContext;
import com.proyectotienda.controller.ProductInputHandler;
import com.proyectotienda.controller.UserInputHandler;
import com.proyectotienda.model.Product;
import com.proyectotienda.model.User;
import com.proyectotienda.repository.DBConnection;
import com.proyectotienda.repository.ProductDAO;
import com.proyectotienda.repository.UserDAO;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    //Launches the app
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/primary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/logginstyle.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Tienda");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/iconTienda.png")));
            stage.show();

            stage.setOnCloseRequest(event -> {
                event.consume();
                logout(stage);
            });
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    //Method to give info with alert in case that someone closes the program, it works in any of the scenes
    public void logout(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Closing the program");
        alert.setHeaderText("You are about to leave");
        alert.setContentText("Do you want to save before exiting? ");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Leaving the program..");
            stage.close();
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();

            // Classes related to the User
            User s = null;
            UserDAO userDAO = new UserDAO(conn);
            UserInputHandler userInputHandler = new UserInputHandler(input);

            // Classes related to the product
            Product p = null;
            ProductDAO productDAO = new ProductDAO(conn);
            ProductInputHandler productInputHandler = new ProductInputHandler(input);


            if (conn != null) {
                //Setting AppContext, which is a static method that has been passing values within scenes
                AppContext.setConnection(conn);
                AppContext.setUserDao(userDAO);
                AppContext.setProductDAO(productDAO);
                AppContext.setUserInputHandler(userInputHandler);
                AppContext.setProductInputHandler(productInputHandler);
                //launches the GUI
                launch(args);

            } else {
                System.out
                        .println("There's a problem with the database, contact or wait for maintenence to address it");
            }
        } catch (SQLException e) {
            System.out.println("Error with the database: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing DB Connection");
            }
        }
        //Method to clean the connection to the database in case that any threads are remaining
        AbandonedConnectionCleanupThread.checkedShutdown();
        input.close();
    }
}