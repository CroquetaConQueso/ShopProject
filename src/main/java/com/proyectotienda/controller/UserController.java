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
        String namePrompt = "Introduce the name that you will use as an user: ";
        String passPrompt = "Introduce your password(It can't have special characters!): ";
        String fundsPrompt = "Introduce your user funds: ";
        User user = User.builder().userName(userInputHandler.getValidatedName(namePrompt))
                .userPass(userInputHandler.getValidatedPassword(passPrompt))
                .userFunds(userInputHandler.getValidatedFunds(fundsPrompt))
                .build();
        boolean check = userDAO.addUser(user);
        if (!check) {
            System.out.println("There was an error adding the user to the database");
            return null;
        }
        return user;
    }

    public User logginUser() {
        String namePrompt = "Introduce the name of the user: ";

        String name = userInputHandler.getValidatedName(namePrompt);

        if (!userDAO.checkUser(name)) {
            System.out.println("The username doesn't exist in the database");
            return null;
        }

        // More feedback?
        return userDAO.logUserDao(name);
    }

    public void deleteUser() {
        String namePrompt = "Introduce the name of the user that you seek to delete: ";
        String name = userInputHandler.getValidatedName(namePrompt);

        if (!userDAO.checkUser(name)) {
            System.out.println("There's no user with the name: " + name);
        } else {
            userDAO.dropUser(name);
        }
    }

    public void modifyUser() {
        String namePrompt = "Introduce the name of the user that you want to search: ";
        String oldName = userInputHandler.getValidatedName(namePrompt);

        if (userDAO.checkUser(oldName)) {
            String newNamePrompt = "Introduce the new name that you will use as an user: ";
            String passPrompt = "Introduce your new password(It can't have special characters!): ";
            String fundsPrompt = "Introduce your new user funds: ";

            String nName = userInputHandler.getValidatedName(newNamePrompt);
            String nPass = userInputHandler.getValidatedPassword(passPrompt);
            float nFunds = userInputHandler.getValidatedFunds(fundsPrompt);

            userDAO.modifyUser(nName, nPass, nFunds, oldName);
        }
    }
}