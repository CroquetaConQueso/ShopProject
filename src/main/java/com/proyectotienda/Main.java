package com.proyectotienda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

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
        System.out.println("1.User Menu");
        System.out.println("2.Product Menu");
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

    private static Product valuesProduct(Scanner input,ProductDAO productDAO) {
            int value1 = 0;
            String value2 = "";
            String value3 = "";
            float value4 = 0;

            System.out.print("Name: ");
            value2 = input.nextLine();
            boolean checkProduct = productDAO.checkProduct(value3);
            if (!checkProduct) {
                System.out.print("Type: ");
                value3 = input.nextLine();
                System.out.print("Price: ");
                value4 = input.nextFloat();
                System.out.print("Quantity: ");
                value1 = input.nextInt();

                Product p = Product.builder()
                        .productName(value2)
                        .productType(value3)
                        .productPrice(value4)
                        .productQuantity(value1)
                        .build();

                boolean success = productDAO.addProduct(p);
                System.out.println(success ? "Product was added succesfully" : "Product was not able to be added");
                if (!success) {
                    return null;
                } else {
                    return p;
                }
            } else {
                System.out.println("A product with that name already exists in the database");
                return null;
            }
    }

    // Added logging to database, didn't have to change much the structure. To keep
    // it working I had to add an empty constructor
    private static User valuesUser(Scanner input, UserDAO userDAO) {
            String value1 = "";
            String value2 = "";
            float value3 = 0;

            input.nextLine();
            System.out.print("User Name: ");
            value2 = input.nextLine();
            boolean checkUser = userDAO.checkUser(value2);
            if (!checkUser) {
                System.out.print("User Pass: ");
                value1 = input.nextLine();
                System.out.print("User Funds: ");
                value3 = input.nextFloat();

                User s = User.builder().userName(value2).userPass(value1).userFunds(value3).build();

                boolean success = userDAO.addUser(s);
                System.out.println(success ? "User inserted" : "Failed to insert user");
                if (!success) {
                    return null;
                } else {
                    return s;
                }
            } else {
                System.out.println("An user with that name already exists in the database");
                return null;
            }


    }

    private static User modifyUser(Scanner input, User user) {
        int switchModU = 0;

        do {
            System.out.println("User Been Modified");
            System.out.println(user);
            System.out.println("What do you wish to modify?");
            System.out.println("1.Name");
            System.out.println("2.Age");
            System.out.println("3.Funds");
            System.out.println("4.Return");

            System.out.print("Option: ");
            try {
                switchModU = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Error: " + e);
                input.nextLine();
            }
            switch (switchModU) {
                case 1:
                    System.out.print("User Name: ");
                    user.setUserName(input.nextLine());
                    break;
                case 2:
                    System.out.print("User Password: ");
                    user.setUserPass(input.nextLine());
                    break;
                case 3:
                    System.out.print("User Funds: ");
                    user.setUserFunds(input.nextFloat());
                    break;
                case 4:
                    System.out.println("Returning to the previous menu..");
                    break;
                default:
                    break;
            }
        } while (switchModU != 4);

        return user;
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
        Connection conn = null;
        Scanner input = new Scanner(System.in);
        ArrayList<User> UserList = new ArrayList<>();
        User s = null;
        UserDAO userDAO = new UserDAO(conn);
        ProductDAO productDAO = new ProductDAO(conn);
        Product p;
        String searchUser = "";
        int switchAnswer = 0;
        int switchAnswer2 = 0;
        int switchAnswer3 = 0;

        try {
            conn = DBConnection.getConnection();
       
        if (conn != null) {
            do {
                // Here we try to log to the database
                menu();
                System.out.print("Introduce a value found on the menu: ");
                try {
                    switchAnswer = input.nextInt();
                    input.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Introduce a number");
                }

                switch (switchAnswer) {
                    case 1:
                        do {
                            userMenu();
                            System.out.print("Introduce a value from on the User Menu: ");
                            try {
                                switchAnswer2 = input.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println(e);
                                input.nextLine();
                            }

                            switch (switchAnswer2) {
                                // 1 Create 2 Delete 3 Show 4 Modify 5 Exit
                                case 1:
                                    System.out.println("Creating User");
                                    s = valuesUser(input,userDAO);
                                    break;
                                case 2:
                                    if (userListCheck(UserList)) {
                                        input.nextLine();
                                        System.out.println("Introduce the name of the user that you want to delete: ");
                                        String SearchUser = input.nextLine();

                                        boolean userCheck = UserList
                                                .removeIf(user -> user.getUserName().equals(SearchUser));

                                        if (!userCheck) {
                                            System.out.println("The user was not able to be deleted");
                                        } else {
                                            System.out.println("The user with the name " + SearchUser + " was deleted");
                                        }
                                    }
                                    break;
                                case 3:
                                    if (userListCheck(UserList)) {
                                        for (User user : UserList) {
                                            System.out.println(user);
                                        }
                                    }
                                    break;
                                case 4:
                                    if (userListCheck(UserList)) {
                                        System.out.println("Introduce the name of the user that you want to modify ");
                                        input.nextLine();
                                        searchUser = input.nextLine();
                                        for (User user : UserList) {
                                            if (user.getUserName().equals(searchUser)) {
                                                modifyUser(input, user);
                                            }
                                        }
                                    }
                                    break;
                                case 5:
                                    System.out.println("Returning to the main menu..");
                                    break;
                                default:
                                    System.out.println("Use a value found on the User Menu");
                                    break;
                            }
                        } while (switchAnswer2 != 5);
                        break;
                    case 2:
                        do {
                            System.out.print("Introduce the name of an user: ");
                            String userNameCheck = input.nextLine();

                            s = userDAO.logUserDao(userNameCheck);
                        } while (s == null);
                        do {
                            productMenu();
                            System.out.print("Option: ");
                            switchAnswer3 = input.nextInt();
                            input.nextLine();
                            switch (switchAnswer3) {
                                case 1:
                                    System.out.println("Creating product");
                                    p = valuesProduct(input, productDAO);
                                    s.getUserCart().addProduct(p);
                                    break;
                                case 2:
                                    System.out.println("wawa");
                                    break;
                                case 3:
                                    s.getUserCart().showCart();
                                    break;
                                case 4:
                                    System.out.println("Returning to the main menu");
                                    break;
                                default:
                                    System.out.println("Introduce a value found on the menu");
                                    break;
                            }
                        } while (switchAnswer3 != 4);
                        break;
                    case 3:

                        break;
                    default:
                        System.out.println("Introduce a value found on the menu");
                        break;
                }
            } while (switchAnswer != 3);
        } else {
            System.out.println("There's a problem with the database, contact or wait for maintenence to address it");
        } } catch (SQLException e) {
            System.out.println("Error with the database: "+e.getMessage());
        }finally{
            try{
                if(conn != null && !conn.isClosed()) conn.close();
            }catch(SQLException e){
                System.out.println("Error closing DB Connection");
            }
        }
        AbandonedConnectionCleanupThread.checkedShutdown();

    }
}