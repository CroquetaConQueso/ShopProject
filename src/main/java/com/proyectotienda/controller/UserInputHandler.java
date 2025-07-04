package com.proyectotienda.controller;

import java.util.Scanner;

public class UserInputHandler {
    private final Scanner input;

    public UserInputHandler(Scanner input) {
        this.input = input;
    }

    public String getValidatedName() {
        System.out.print("Introduce the name for the user: ");
        String name = input.nextLine();
        if (name.length() > 50) {
            System.out.println("The name can't be longer than 50 characters");
            return getValidatedName();
        } else if (name.length() < 2) {
            System.out.println("The name needs to have more than 2 characters");
            return getValidatedName();
        }

        return name;
    }

    public String getValidatedPassword() {
        System.out.print("Introduce your password: ");
        String specialchars = "%$~&@";
        String password = input.nextLine();
        if (password.length() < 7) {
            System.out.println("The password can't have less than 7 characters");
            return getValidatedPassword();
        } else if (password.length() > 50) {
            System.out.println("The password can't be longer than 50 characters");
            return getValidatedPassword();
        }
        for (int i = 0; i < password.length(); i++) {
            if (password.contains(String.valueOf(specialchars.charAt(i)))) {
                System.out.println("The password can't have any of these characters: " + specialchars);
                return getValidatedPassword();
            }
        }

        System.out.println("The password has been successfully created");
        return password;

    }

    public float getValidatedFunds() {
        System.out.println("Introduce the funds: ");
        String funds = input.nextLine().replace(',', '.');

        if (!funds.matches("^\\d+(\\.\\d{1,2})?$")) {
            System.out.println("Invalid funds format. Only numbers with up to two decimal places are allowed.");
            return getValidatedFunds();
        }
        float convertedFunds = Float.parseFloat(funds);

        if (convertedFunds <= 0) {
            System.out.println("You can't have zero funds or negative funds");
            return getValidatedFunds();
        }

        return convertedFunds;
    }
}