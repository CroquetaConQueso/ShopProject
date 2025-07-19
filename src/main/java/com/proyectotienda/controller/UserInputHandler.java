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
        } else if (name.length() < 2) {
            System.out.println("The name needs to have more than 2 characters");
        }

        return name;
    }

    public static boolean getValidatedName1(String name) {

        if (name.length() > 50) {
            return false;
        } else if (name.length() < 2) {
            return false;
        }

        return true;
    }

    public static boolean getValidatedPassword1(String password) {
        int sum = 0;

        if (password.length() < 7) {
            return false;
        } else if (password.length() > 50) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                return false;
            }
            if (Character.isDigit(password.charAt(i))) {
                sum += 1;
            }
        }
        if (sum == 0) {
            return false;
        }
        return true;

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