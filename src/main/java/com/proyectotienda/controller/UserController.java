package com.proyectotienda.controller;

//This class doesn't have much meaning anymore within a GUI setting, therefore it will no longer be supported nor held in the AppContext


//It will control the flow of the creation and the connectivity of all the other User classes
import com.proyectotienda.model.User;
import com.proyectotienda.repository.UserDAO;

public class UserController {
    private final  UserDAO userDAO;
    private final UserInputHandler userInputHandler;

    public UserController(UserDAO userDAO, UserInputHandler userInputHandler) {
        this.userDAO = userDAO;
        this.userInputHandler = userInputHandler;
    }

    public UserDAO getUserDAO(){
        return userDAO;
    }

    public User createUser(String username, String user_pass, String userFunds) {
        
        if(!userInputHandler.getValidatedName(username)){return null;}
        else if(!userInputHandler.getValidatedPassword(user_pass)){return null;}
        else if(!userInputHandler.getValidatedFunds(userFunds)){return null;}

        float newfunds = Float.parseFloat(userFunds);

        User user = User.builder().userName(username)
                .userPass(user_pass)
                .userFunds(newfunds)
                .build();
        boolean check = userDAO.addUser(user);
        if (!check) {
            System.out.println("There was an error adding the user to the database");
            return null;
        }
        return user;
    }

    public  User logginUser(String userName,String userPassword) {

        if (!userDAO.checkUser(userName,userPassword)) {
            System.out.println("The username doesn't exist in the database");
            return null;
        }
        // More feedback?
        return userDAO.logUserDao(userName);
    }


    public boolean deleteUser(String userName, String userPassword) {

        if (!userDAO.checkUser(userName, userPassword)) {
            return false;
        } else {
            userDAO.dropUser(userName, userPassword);
            return true;
        }
    }

    /* It doesn't have any logic any more within a GUI setting 
    public User modifyUser(User s){

         if (userDAO.checkUser(s.getUserName(),s.getUserPass())) {
            String newNamePrompt = "Introduce the new name that you will use as an user: ";
            String newPassPrompt = "Introduce your new password(It can't have special characters!): ";
            String fundsPrompt = "Introduce your new user funds: ";

            String nName = userInputHandler.getValidatedName(newNamePrompt);
            String nPass = userInputHandler.getValidatedPassword(newPassPrompt);
            float nFunds = userInputHandler.getValidatedFunds(fundsPrompt);

            userDAO.modifyUser(nName, nPass, nFunds, oldName);
        }

        return s;
    }
        */

    /*  This will be reworked for the future Admin class
    public void modifyUser() {
        String oldName = userInputHandler.getValidatedName(namePrompt);
        String pass = userInputHandler.getValidatedPassword(passPrompt);

        if (userDAO.checkUser(oldName,passPrompt)) {
            String newNamePrompt = "Introduce the new name that you will use as an user: ";
            String newPassPrompt = "Introduce your new password(It can't have special characters!): ";
            String fundsPrompt = "Introduce your new user funds: ";

            String nName = userInputHandler.getValidatedName(newNamePrompt);
            String nPass = userInputHandler.getValidatedPassword(newPassPrompt);
            float nFunds = userInputHandler.getValidatedFunds(fundsPrompt);

            userDAO.modifyUser(nName, nPass, nFunds, oldName);
        }
    }
        */
}