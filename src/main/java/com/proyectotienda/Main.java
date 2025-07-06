package com.proyectotienda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.proyectotienda.controller.ProductController;
import com.proyectotienda.controller.ProductInputHandler;
import com.proyectotienda.controller.UserController;
import com.proyectotienda.controller.UserInputHandler;
import com.proyectotienda.model.Cart;
import com.proyectotienda.model.Product;
import com.proyectotienda.model.User;
import com.proyectotienda.repository.DBConnection;
import com.proyectotienda.repository.ProductDAO;
import com.proyectotienda.repository.UserDAO;

/*Cambiar logica del attemptlog a: 
 * Connection attemptLog() que retorna conn, luego en main
 * productDAO(?)
 * userDAO(?)
 * Puede que no sea tan útil
*/
public class Main {
    private static void menu() {
        System.out.println("Menu:");
        System.out.println("1.Log in");
        System.out.println("2.Register");
        System.out.println("3.Exit");
    }

    private static void userMenu() {
        System.out.println("User Menu:");
        System.out.println("1.Create User");
        System.out.println("2.Delete User");
        System.out.println("3.Show Users");
        System.out.println("4.Modify User");
        System.out.println("5.Return to Main Menu");
    }

    private static void productMenu() {
        System.out.println("Product Menu");
        System.out.println("1.Add Product");
        System.out.println("2.Remove Product");
        System.out.println("3.Show User Cart");
        System.out.println("4.Return to Main Menu");
    }

    private static boolean userListCheck(ArrayList<User> userList) {
        if (userList.isEmpty()) {
            System.out.println("There are no users");
            return false;
        } else {
            return true;
        }
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
                            UserList.clear();
                            s = userController.logginUser();
                            System.out.println("You have logged succesfully as " + s.getUserName());
                            UserList.add(s);

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
                                        p = productController.createProduct();
                                        userCart.addProduct(p);
                                        s.setUserCart(userCart);
                                        System.out.println(s);
                                        break;
                                    case 2:
                                        productController.checkTypesProducts();
                                        break;
                                    case 3:
                                        userCart = s.getUserCart();
                                        System.out.println(userCart.getProducts());
                                        break;
                                    case 4:
                                        System.out.println("Returning to the main menu..");
                                        break;
                                    default:
                                        break;
                                }
                            } while (switchAnswer2 != 4);
                            break;
                        case 2:
                            System.out.println("Creating User");
                            s = userController.createUser();
                            System.out.println("The user "+s.getUserName()+" has been created! Redirecting you to the main menu.");
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