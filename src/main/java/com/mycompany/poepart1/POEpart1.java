
package com.mycompany.poepart1;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;



public class POEpart1 {
    public static void main(String[] args) {
        String username;
        do {
            username = JOptionPane.showInputDialog("Enter username (max 5 characters, must contain _):");
            if (!Login.checkusername(username)) {
                JOptionPane.showMessageDialog(null, "Invalid username. It must contain an underscore and be no more than 5 characters.");
            }
        } while (!Login.checkusername(username));

        String password;
        do {
            password = JOptionPane.showInputDialog("Enter password (min 8 characters, 1 uppercase, 1 number, 1 special char):");
            if (!Login.checkPasswordComplexity(password)) {
                JOptionPane.showMessageDialog(null, "Invalid password. It must contain at least 8 characters, a capital letter, a number, and a special character.");
            }
        } while (!Login.checkPasswordComplexity(password));

        String number;
        do {
            number = JOptionPane.showInputDialog("Enter phone number with country code (e.g., +270677306577):");
            if (!Login.checkCellPhoneNumber(number)) {
                JOptionPane.showMessageDialog(null, "Invalid phone number. Must start with '+27' and contain only digits afterward.");
            }
        } while (!Login.checkCellPhoneNumber(number));

        String firstName = JOptionPane.showInputDialog("Enter first name:");
        String lastName = JOptionPane.showInputDialog("Enter last name:");

        Login login = new Login(username, password, firstName, lastName, number);
        Login.registerUser(login.getuserName(), login.getpassword(), login.getnumber());

        String enteredUsername = JOptionPane.showInputDialog("Enter your username:");
        String enteredPassword = JOptionPane.showInputDialog("Enter your password:");
        String loginSuccess = login.returnLoginStatus(enteredUsername, enteredPassword, firstName, lastName);

        if (loginSuccess.startsWith("Welcome")) {
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");
            Messages.loadStoredMessages();
            int sentMessages = 0;
            int messageNumber = 1;
            int confirm;

            do {

                String recipient;
                do {
                    recipient = JOptionPane.showInputDialog("Enter recipient number (e.g., +270612345678):");
                    if (!Messages.checkRecipientCell(recipient)) {
                        JOptionPane.showMessageDialog(null, "Invalid recipient number. Must start with '+27' and have exactly 10 digits after.");
                    }
                } while (!Messages.checkRecipientCell(recipient));

                String messageText = JOptionPane.showInputDialog("Enter message text:");
                Messages message = new Messages(messageNumber, recipient, messageText);

                String[] options = {"Send", "Disregard", "Store"};
                int action = JOptionPane.showOptionDialog(null, "Choose what to do with the message:", "Message Options",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                String result = Messages.sendMessageAction(String.valueOf(action + 1));

                switch (result) {
                    case "Sent" -> {
                        Messages.sentMessages.add(message);
                        Messages.messageHashes.add(message.getMessageHash());
                        Messages.messageIDs.add(message.getMessageID());
                        message.storeMessage(); // Store to JSON
                        JOptionPane.showMessageDialog(null, "Message sent successfully.\n" + message.formatMessage());
                        sentMessages++;
                    }
                    case "Stored" -> {
                        Messages.storedMessages.add(message);
                        Messages.messageHashes.add(message.getMessageHash());
                        Messages.messageIDs.add(message.getMessageID());
                        message.storeMessage(); // Store to JSON
                        JOptionPane.showMessageDialog(null, "Message stored successfully.\n" + message.formatMessage());
                        sentMessages++;
                    }
                    case "Disregarded" -> {
                        Messages.disregardedMessages.add(message);
                        JOptionPane.showMessageDialog(null, "Message disregarded.");
                    }
                    default -> JOptionPane.showMessageDialog(null, "Unknown message action.");
                }

                messageNumber++;
                confirm = JOptionPane.showConfirmDialog(null, "Would you like to send another message?", "Continue", JOptionPane.YES_NO_OPTION);
            } while (confirm == JOptionPane.YES_OPTION);

            JOptionPane.showMessageDialog(null, "You have sent/stored " + sentMessages + " message(s).");

            String[] options = {
                    "Display Sender & Recipients",
                    "Display Longest Message",
                    "Search by Message ID",
                    "Search by Recipient",
                    "Delete by Message Hash",
                    "Display Full Report",
                    "Exit"
            };

            int choice;
            do {
                choice = JOptionPane.showOptionDialog(null,
                        "Choose an action:",
                        "QuickChat Message Options",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                switch   (choice) {
                    case 0 -> { // a. Display sender and recipient
                        StringBuilder sb = new StringBuilder("Sent Messages:\n");
                        for (Messages msg : Messages.sentMessages) {
                            sb.append("Sender: YOU\nRecipient: ")
                                    .append(msg.getRecipient()).append("\n\n");
                        }
                        JOptionPane.showMessageDialog(null, sb.toString());
                    }

                    case 1 -> { // b. Display longest message
                        Messages longest = null;
                        for (Messages msg : Messages.sentMessages) {
                            if (longest == null || msg.getMessageText().length() > longest.getMessageText().length()) {
                                longest = msg;
                            }
                        }
                        if (longest != null) {
                            JOptionPane.showMessageDialog(null, "Longest Message:\n" + longest.getMessageText());
                        } else {
                            JOptionPane.showMessageDialog(null, "No sent messages yet.");
                        }
                    }

                    case 2 -> { // c. Search by message ID
                        String id = JOptionPane.showInputDialog("Enter the Message ID to search:");
                        boolean found = false;
                        for (Messages msg : Messages.sentMessages) {
                            if (msg.getMessageID().equals(id)) {
                                JOptionPane.showMessageDialog(null,
                                        "Recipient: " + msg.getRecipient() + "\nMessage: " + msg.getMessageText());
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            JOptionPane.showMessageDialog(null, "Message ID not found.");
                        }
                    }

                    case 3 -> { // d. Search by recipient
                        String recipient = JOptionPane.showInputDialog("Enter recipient number to search:");
                        StringBuilder result = new StringBuilder();
                        for (Messages msg : Messages.sentMessages) {
                            if (msg.getRecipient().equals(recipient)) {
                                result.append(msg.getMessageText()).append("\n");
                            }
                        }
                        if (result.length() > 0) {
                            JOptionPane.showMessageDialog(null, "Messages sent to " + recipient + ":\n" + result);
                        } else {
                            JOptionPane.showMessageDialog(null, "No messages found for this recipient.");
                        }
                    }

                    case 4 -> { // e. Delete by message hash
                        String hash = JOptionPane.showInputDialog("Enter the message hash to delete:");
                        Iterator<Messages> iterator = Messages.sentMessages.iterator();
                        boolean deleted = false;
                        while (iterator.hasNext()) {
                            Messages msg = iterator.next();
                            if (msg.getMessageHash().equals(hash)) {
                                iterator.remove();
                                JOptionPane.showMessageDialog(null,
                                        "Message \"" + msg.getMessageText() + "\" successfully deleted.");
                                deleted = true;
                                break;
                            }
                        }
                        if (!deleted) {
                            JOptionPane.showMessageDialog(null, "Message hash not found.");
                        }
                    }

                    case 5 -> { // f. Display full report
                        if (Messages.sentMessages.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No sent messages to display.");
                        } else {
                            StringBuilder report = new StringBuilder("Sent Message Report:\n\n");
                            for (Messages msg : Messages.sentMessages) {
                                report.append("Message Hash: ").append(msg.getMessageHash()).append("\n")
                                        .append("Recipient: ").append(msg.getRecipient()).append("\n")
                                        .append("Message: ").append(msg.getMessageText()).append("\n")
                                        .append("Message ID: ").append(msg.getMessageID()).append("\n\n");
                            }
                            JOptionPane.showMessageDialog(null, report.toString());
                        }
                    }

                    case 6 -> JOptionPane.showMessageDialog(null, "Thank you for using QuickChat. Goodbye!");

                    default -> {}
                }

            } while (choice != 6);

        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Exiting application.");
        }
    }
    }


