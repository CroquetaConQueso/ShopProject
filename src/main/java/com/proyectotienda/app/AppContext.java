package com.proyectotienda.app;

import java.sql.Connection;

import com.proyectotienda.controller.ProductInputHandler;
import com.proyectotienda.controller.UserInputHandler;
import com.proyectotienda.repository.ProductDAO;
import com.proyectotienda.repository.UserDAO;

public class AppContext {
    private static UserDAO userDAO;
    private static ProductDAO productDAO;
    private static UserInputHandler userInputHandler;
    private static ProductInputHandler productInputHandler;
    private static Connection connection;
    private static String word;

    public static ProductDAO geProductDAO(){
        return productDAO;
    }

    public static void setProductDAO(ProductDAO pDAO){
        productDAO = pDAO;
    }

    public static UserInputHandler getUserInputHandler(){
        return userInputHandler;
    }

    public static void setUserInputHandler(UserInputHandler uIHandler){
        userInputHandler = uIHandler;
    }

    public static ProductInputHandler getProductInputHandler(){
        return productInputHandler;
    }

    public static void setProductInputHandler(ProductInputHandler pIHandler){
        productInputHandler = pIHandler;
    }

    public static UserDAO getUserDAO(){
        return userDAO;
    }

    public static void setUserDao(UserDAO uD){
        userDAO = uD;
    }

    public static String getUserWord (){
        return word;
    }

    public static void setWord (String newword){
        word = newword;
    }

    public static void setConnection(Connection c) {
        connection = c;
    }

    public static Connection getConnection() {
        return connection;
    }
}
