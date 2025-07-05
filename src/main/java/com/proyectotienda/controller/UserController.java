package com.proyectotienda.controller;

//It will control the flow of the creation and the connectivity of all the other User classes
import com.proyectotienda.model.User;
import com.proyectotienda.repository.UserDAO;

public class UserController {
    private final UserDAO userDAO;
    private final UserInputHandler userInputHandler;

    public UserController(UserDAO userDAO, UserInputHandler userInputHandler) {
        this.userDAO = userDAO;
        this.userInputHandler = userInputHandler;
    }

    public User createUser() {
        User user = User.builder().userName(userInputHandler.getValidatedName())
                .userPass(userInputHandler.getValidatedPassword()).userFunds(userInputHandler.getValidatedFunds())
                .build();
        boolean check = userDAO.addUser(user);
        if(!check){
            System.out.println("There was an error adding the user to the database");
            return null;
        }
        return user;
    }
}