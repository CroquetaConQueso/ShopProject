package com.proyectotienda.controller;

import java.util.Scanner;

public class UserInputHandler {
    private final Scanner input;

    public UserInputHandler(Scanner input) {
        this.input = input;
    }

    public String getValidatedName(String name) {
        
        if (name.length() > 50) {
            System.out.println("The name can't be longer than 50 characters");
            return getValidatedName(name);
        } else if (name.length() < 2) {
            System.out.println("The name needs to have more than 2 characters");
            return getValidatedName(name);
        }

        return name;
    }


    public String getValidatedPassword1(String password) {
        if (password.length() < 7) {
            System.out.println("The password can't have less than 7 characters");
            return getValidatedPassword(password);
        } else if (password.length() > 50) {
            System.out.println("The password can't be longer than 50 characters");
            return getValidatedPassword(password);
        }
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                System.out.println("The password can only have letters or numbers");
                return getValidatedPassword(password);
            }
        }
        return password;

    }

    public String getValidatedPassword(String prompt) {
        System.out.print(prompt);
        String password = input.nextLine();
        if (password.length() < 7) {
            System.out.println("The password can't have less than 7 characters");
            return getValidatedPassword(prompt);
        } else if (password.length() > 50) {
            System.out.println("The password can't be longer than 50 characters");
            return getValidatedPassword(prompt);
        }
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                System.out.println("The password can only have letters or numbers");
                return getValidatedPassword(prompt);
            }
        }
        return password;

    }

    public float getValidatedFunds(String prompt) {
        System.out.print(prompt);
        String funds = input.nextLine().replace(',', '.');

        if (!funds.matches("^\\d+(\\.\\d{1,2})?$")) {
            System.out.println("Invalid funds format. Only numbers with up to two decimal places are allowed.");
            return getValidatedFunds(prompt);
        }
        float convertedFunds = Float.parseFloat(funds);

        if (convertedFunds <= 0) {
            System.out.println("You can't have zero funds or negative funds");
            return getValidatedFunds(prompt);
        }

        return convertedFunds;
    }
}