
package com.mycompany.poepart1;

import java.util.Scanner;
import javax.swing.JOptionPane;


/**
 *
 * @author RC_Student_lab
 */
public class Login {
 
        
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String number;
    
    
    public Login(String username,String password,String firstName,String lastName,String number){
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.number = number;
    
    checkusername(username);
    checkPasswordComplexity(password);
    checkCellPhoneNumber(number);
    
}
    public String getuserName(){
        return username;
    }
    public void setuserName(String username){
        this.username = username;
    }
    public String getpassword(){
        return password;
    }
    public void setpassword(String password){
        this.password = password;
    }
    public String getfirstName(){
        return firstName;
    }
    public void setfirstName(String firstName){
        this.firstName = firstName;
    }
    public String getlastName(){
        return lastName;
    }  
    public void setlastName(String lastName){
        this.lastName = lastName;
    }
    public String getnumber(){
        return number;
    }
    public void setnumber(String number){
        this.number = number;
    }
            
    
    
    
    public static boolean checkusername(String username){
         if (username.contains("_")&& username.length()<=5) {
             return true;
         }
         if (!username.contains("_")&& username.length()<=5){
             return false;
         }
        return false;
         
         }
    public static boolean checkPasswordComplexity(String password) {
        if (password.length() >= 8 &&
           password.matches(".*[A-Z].*")&&
           password.matches(".*[a-z].*")&&
           password.matches(".*[0-9].*")&&
           password.matches(".*[!@#$%^&*()].*")) {
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean checkCellPhoneNumber(String number) { //OpenAI. (2025). ChatGPT (Apr 4 version) [Large language model]. https://chat.openai.com/chat
        // Check if it starts with '+'
        if (number == null || !number.startsWith("+")) {
            return false;
        }

        // Remove '+' and check the rest
        String digits = number.substring(1);

        // Check if the rest is only digits
        if (!digits.matches("\\d+")) {
            return false;
        }

        // Check country code (1 to 3 digits) and local number (up to 10 digits)
        for (int i = 1; i <= 3 && i < digits.length(); i++) {
            String countryCode = digits.substring(0, i);
            String localNumber = digits.substring(i);

            if (localNumber.length() <= 10) {
                return true;
            }
        }

        return false;
    }
    public static String registerUser(String username, String password, String number) {
    StringBuilder output = new StringBuilder();

    if (checkusername(username)) {
        JOptionPane.showMessageDialog(null, "Username successfully captured");
    } else {
        JOptionPane.showMessageDialog(null,
                "Username is not correctly formatted. Please ensure that your username contains an underscore and is no more than five characters in length.");
    }

    if (checkPasswordComplexity(password)) {
        JOptionPane.showMessageDialog(null, "Password successfully captured");
    } else {
        JOptionPane.showMessageDialog(null,
                "Password is not correctly formatted. It must be at least 8 characters long, contain a capital letter, a number, and a special character.");
    }

    if (checkCellPhoneNumber(number)) {
        JOptionPane.showMessageDialog(null, "Cell phone number correctly added");
    } else {
        JOptionPane.showMessageDialog(null,
                "Cell phone number incorrectly formatted or does not contain international code.");
    }

    if (checkusername(username) && checkPasswordComplexity(password)) {
        JOptionPane.showMessageDialog(null, "You are now registered.");
    }

    return output.toString();
}
    public static boolean loginUser(String enteredUsername, String enteredPassword,String username, String password){
    return username.equals(enteredUsername) && password.equals(enteredPassword);
    }
    public String returnLoginStatus(String enteredUsername, String enteredPassword, String firstName, String lastName) {
    if (loginUser(enteredUsername, enteredPassword, username, password)) {
        return "Welcome " + firstName + " " + lastName + ", it is great to see you.";
    } else {
        return "Login failed";
    }
}
    }
