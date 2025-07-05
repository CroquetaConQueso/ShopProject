package com.proyectotienda.controller;

import java.util.Scanner;

public class UserInputHandler {
    private final Scanner input;

    public UserInputHandler(Scanner input) {
        this.input = input;
    }

    public String getValidatedName(String prompt) {
        System.out.print(prompt);
        String name = input.nextLine();
        if (name.length() > 50) {
            System.out.println("The name can't be longer than 50 characters");
            return getValidatedName(prompt);
        } else if (name.length() < 2) {
            System.out.println("The name needs to have more than 2 characters");
            return getValidatedName(prompt);
        }

        return name;
    }

    public String getValidatedPassword(String prompt) {
        System.out.print(prompt);
        String specialchars = "%$~&@";
        String password = input.nextLine();
        if (password.length() < 7) {
            System.out.println("The password can't have less than 7 characters");
            return getValidatedPassword(prompt);
        } else if (password.length() > 50) {
            System.out.println("The password can't be longer than 50 characters");
            return getValidatedPassword(prompt);
        }
        for (int i = 0; i < password.length(); i++) {
            if (password.contains(String.valueOf(specialchars.charAt(i)))) {
                System.out.println("The password can't have any of these characters: " + specialchars);
                return getValidatedPassword(prompt);
            }
        }

        System.out.println("The password has been successfully created");
        return password;

    }

    public float getValidatedFunds(String prompt) {
        System.out.println(prompt);
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