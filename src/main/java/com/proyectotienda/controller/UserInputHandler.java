package com.proyectotienda.controller;

public class UserInputHandler {
    //I think this class could have been a static class, yet I have kept it as a "normal class" just in case
    public  boolean getValidatedName(String name) {

        if (name.length() > 50) {
            return false;
        } else if (name.length() < 2) {
            return false;
        }

        return true;
    }

    public  boolean getValidatedPassword(String password) {
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

    public boolean getValidatedFunds(String funds) {
        funds = funds.replace(',', '.');

        if (!funds.matches("^\\d+(\\.\\d{1,2})?$")) {
            System.out.println("Invalid funds format. Only numbers with up to two decimal places are allowed.");
            return false;
        }

        float convertedFunds = Float.parseFloat(funds);

        if (convertedFunds <= 0) {
            System.out.println("You can't have zero funds or negative funds");
            return false;
        }

        return true;
    }
}