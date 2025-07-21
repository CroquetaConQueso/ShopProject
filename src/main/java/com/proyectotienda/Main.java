package com.proyectotienda;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.proyectotienda.controller.ProductController;
import com.proyectotienda.controller.ProductInputHandler;
import com.proyectotienda.controller.SceneControllerLogin;
import com.proyectotienda.controller.UserController;
import com.proyectotienda.controller.UserInputHandler;
import com.proyectotienda.model.Cart;
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

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/primary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
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

    public void logout(Stage stage){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Closing the program");
        alert.setHeaderText("You are about to leave");
        alert.setContentText("Do you want to save before exiting? ");

        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("Leaving the program..");
            stage.close();
        }
    }
    
    private static void menu() {
        System.out.println("Menu:");
        System.out.println("1.Log in");
        System.out.println("2.Register");
        System.out.println("3.Exit");
    }

    private static void productMenu() {
        System.out.println("Product Menu");
        System.out.println("1.Buy Product");
        System.out.println("2.Remove Product");
        System.out.println("3.Show products");
        System.out.println("4.Show User Cart");
        System.out.println("5.Return to Main Menu");
    }

    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        Connection conn = null;
        // Lists
        ArrayList<User> UserList = new ArrayList<>();
        
        // Values
        int switchAnswer = 0;
        int switchAnswer2 = 0;
        
        try {
            conn = DBConnection.getConnection();
            
            // Classes related to the User
            User s = null;
            UserDAO userDAO = new UserDAO(conn);
            UserInputHandler userInputHandler = new UserInputHandler(input);
            UserController userController = new UserController(userDAO, userInputHandler);
            
            // Classes related to the product
            Product p = null;
            ProductDAO productDAO = new ProductDAO(conn);
            ProductInputHandler productInputHandler = new ProductInputHandler(input);
            ProductController productController = new ProductController(productDAO, productInputHandler);
            
            Cart userCart = new Cart();
            
            launch(args);


            if (conn != null) {
                do {
                    menu();
                    System.out.print("Introduce a value found on the menu: ");
                    try {
                        switchAnswer = input.nextInt();
                        input.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Introduce a number found on the menu");
                    }
                    switch (switchAnswer) {
                        case 1:
                            //Cleaning from memory
                            UserList.clear();
                            //We try to log as said user
                            s = userController.logginUser();
                            System.out.println("You have logged succesfully as " + s.getUserName());
                            UserList.add(s);
                            s.setUserCart(userCart);
                            do {
                                productMenu();
                                System.out.print("Choose an option: ");
                                try {
                                    switchAnswer2 = input.nextInt();
                                    input.nextLine();
                                } catch (InputMismatchException e) {
                                    System.out.println("Introduce a number found on the menu");
                                }
                                switch (switchAnswer2) {
                                    case 1:
                                        System.out.println();
                                        p = productController.userBuysProduct(s);
                                        break;
                                    case 2:
                                        System.out.println("Nombre del producto que vas a eliminar");
                                        s.getUserCart().removeProduct(p, switchAnswer2);
                                        break;
                                    case 3:
                                        productController.showAllProducts();
                                        break;
                                    case 4:
                                        s.getUserCart().showCart();
                                        s.getUserCart().calculateTotal();
                                        break;
                                    case 5:
                                        System.out.println("Returning to the main menu..");
                                        break;
                                    default:
                                        System.out.println("Insert a value found on the menu");
                                        break;
                                }
                            } while (switchAnswer2 != 5);
                            break;
                        case 2:
                            System.out.println("Creating User");
                            s = userController.createUser();
                            System.out.println("The user " + s.getUserName()
                                    + " has been created! Redirecting you to the main menu.");
                            break;
                        case 3:
                            System.out.println("Closing the program..");
                            break;
                        default:
                            System.out.println("Use a value found on the User Menu");
                            break;
                    }
                } while (switchAnswer != 3);
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
        AbandonedConnectionCleanupThread.checkedShutdown();
        input.close();
    }
}