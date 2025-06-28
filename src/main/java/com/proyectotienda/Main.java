package com.proyectotienda;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.proyectotienda.model.Product;

public class Main {
    
    private static void menuAccess(){
        System.out.println("+------------+");
        System.out.println("|  APP MENU  |");
        System.out.println("+------------+");
        System.out.println("1.Sign in");
        System.out.println("2.Register");
        System.out.println("3.Exit");
    }

    private static void menuLogged(){
        System.out.println("+------------+");
        System.out.println("|  APP MENU  |");
        System.out.println("+------------+");
        System.out.println("1.Sign in");
        System.out.println("2.Register");
        System.out.println("3.Exit");
        System.out.println("4.");
        System.out.println("5.");
        System.out.println("6.");
        System.out.println("7.");
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        int answerSwitch = 0;
        String valueSt = "";
        String valueSt2 = "";
        int valueInt = 0;

        do {
            menuAccess();
            System.out.print("Introduce a value: ");
            try{
                answerSwitch = input.nextInt();
            }catch(InputMismatchException e){System.out.println(e);}
            
            switch (answerSwitch) {
                case 1:
                    Product newProducto = new Product(valueSt,valueSt2,valueInt);            
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