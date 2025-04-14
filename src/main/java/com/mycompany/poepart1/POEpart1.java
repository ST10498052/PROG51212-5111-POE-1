/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poepart1;

import java.util.Scanner;

/**
 *
 * @author RC_Student_lab
 */
public class POEpart1 {

    public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);
    
    String username;
        do {
            System.out.print("Enter username (max 5 characters, must contain _): ");
            username = scanner.nextLine();
            if (!Login.checkusername(username)) {
                System.out.println("Invalid username. It must contain an underscore and be no more than 5 characters.");
            }
        } while (!Login.checkusername(username));

        String password;
        do {
            System.out.print("Enter password (min 8 characters, 1 uppercase, 1 number, 1 special char): ");
            password = scanner.nextLine();
            if (!Login.checkPasswordComplexity(password)) {
                System.out.println("Invalid password. It must contain at least 8 characters, a capital letter, a number, and a special character.");
            }
        } while (!Login.checkPasswordComplexity(password));

        String number;
        do {
            System.out.print("Enter phone number with country code (e.g., +270677306577): ");
            number = scanner.nextLine();
            if (!Login.checkCellPhoneNumber(number)) {
                System.out.println("Invalid phone number. Must start with '+' and contain only digits afterward.");
            }
        } while (!Login.checkCellPhoneNumber(number));

        // (no formatting rules)
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
    
    Login login = new Login(username,password,firstName,lastName,number);
   
       
        login.registerUser(login.getuserName(), login.getpassword(), login.getnumber());
        
        
        System.out.print("Enter your username: ");
        String enteredUsername = scanner.nextLine();
        System.out.print("Enter your password: ");
        String enteredPassword = scanner.nextLine();
       
        String loginSuccess = login.returnLoginStatus(enteredUsername, enteredPassword,login.getfirstName(),login.getlastName());
        System.out.println(loginSuccess);
        
    }
}
