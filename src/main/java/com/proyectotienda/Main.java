package com.proyectotienda;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.proyectotienda.model.Product;

public class Main {
    //Menu dedicated to accessing the app
    private static void menuAccess(){
        System.out.println("+------------+");
        System.out.println("|  APP MENU  |");
        System.out.println("+------------+");
        System.out.println("1.Sign in");
        System.out.println("2.Register");
        System.out.println("3.Exit");
    }
    //Menu dedicated to logging within the app
    private static void menuLogged(){
        System.out.println("+------------+");
        System.out.println("|  APP MENU  |");
        System.out.println("+------------+");
        System.out.println("1.Account");
        System.out.println("2.Products");
        System.out.println("3.Cart");
        System.out.println("4.Payment");
        System.out.println("5.Exit");
    }

    /*private static Product makingProduct(){

        
    }*/

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        int answerSwitch = 0;
        String valueSt = "";
        String valueSt2 = "";
        int valueInt = 0;
        int valueInt2 = 0;
        float valueFloat = 0;

        do {
            menuAccess();
            System.out.print("Introduce a value: ");
            try{
                answerSwitch = input.nextInt();
            }catch(InputMismatchException e){System.out.println(e);}
            
            switch (answerSwitch) {
                case 1:
                    //ID , name , type , price, quantity
                    System.out.print("ID: ");
                    valueInt = input.nextInt();
                    input.nextLine();
                    System.out.print("Name: ");
                    valueSt = input.nextLine();
                    System.out.print("Type: ");
                    valueSt = input.nextLine();
                    System.out.print("Price: ");
                    valueFloat = input.nextFloat();
                    System.out.print("Quantity: ");
                    valueInt2 = input.nextInt();

                    Product newProducto = new Product(valueInt,valueSt,valueSt2,valueFloat,valueInt);
                    System.out.println(newProducto);            
                    break;
                case 2:
                    System.out.println("Wawa");
                    break;
                case 3:
                    System.out.println("Leaving the program");
                    break;
                default:
                    System.out.println("Use a value found on the menu");
                    break;
            }
        } while (answerSwitch != 3);
    }
}