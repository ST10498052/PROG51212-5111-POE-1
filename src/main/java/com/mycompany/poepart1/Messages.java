/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepart1;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Messages {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    // Constructor that auto-generates ID and Hash
    public Messages(int messageNumber, String recipient, String messageText) {
        this.messageID = generateRandomMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash(this.messageID, messageNumber, messageText);
    }

    // Generate random 10-digit ID
    public static String generateRandomMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    // Validate message ID
    public static boolean checkMessageID(String id) {
        return id.length() <= 10;
    }

    // Validate recipient phone number
    public static boolean checkRecipientCell(String number) {
        if (!number.startsWith("+27")) return false;
        return number.matches("\\+27\\d{1,10}");
    }

    // Create hash
    public static String createMessageHash(String messageID, int messageNumber, String text) {
        String[] words = text.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;
        return (messageID.substring(0, 2) + ":" + messageNumber + ":" + first + last).toUpperCase();
    }

    // Handle user choice
    public static String sendMessageAction(String userChoice) {
        return switch (userChoice.trim()) {
            case "1" -> "Sent";
            case "2" -> "Disregarded";
            case "3" -> "Stored";
            default -> "Disregarded";
        };
    }

    // Store message in JSON file
    public void storeMessage() {
        JSONObject obj = new JSONObject();
        obj.put("MessageID", messageID);
        obj.put("MessageNumber", messageNumber);
        obj.put("Recipient", recipient);
        obj.put("MessageText", messageText);
        obj.put("MessageHash", messageHash);

        try (FileWriter file = new FileWriter("storedMessages.json", true)) {
            file.write(obj.toJSONString() + "\n");
        } catch (IOException e) {
            System.err.println("Error saving message: " + e.getMessage());
        }
    }

    // Return formatted message for display
    public String formatMessage() {
        return "MessageID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageText;
    }

    // Getters
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }

    public static ArrayList<Messages> sentMessages = new ArrayList<>();
    public static ArrayList<Messages> disregardedMessages = new ArrayList<>();
    public static ArrayList<Messages> storedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIDs = new ArrayList<>();

    public static void displaySendersAndRecipients() {
        for (Messages msg : sentMessages) {
            System.out.println("Sender: YOU, Recipient: " + msg.getRecipient());
        }
    }
    public static void displayLongestMessage() {
        Messages longest = null;
        for (Messages msg : sentMessages) {
            if (longest == null || msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        if (longest != null) {
            System.out.println("Longest Message: " + longest.getMessageText());
        }
    }
    public static void searchByMessageID(String id) {
        for (Messages msg : sentMessages) {
            if (msg.getMessageID().equals(id)) {
                System.out.println("Recipient: " + msg.getRecipient());
                System.out.println("Message: " + msg.getMessageText());
                return;
            }
        }
        System.out.println("Message ID not found.");
    }
    public static void searchByRecipient(String recipient) {
        for (Messages msg : sentMessages) {
            if (msg.getRecipient().equals(recipient)) {
                System.out.println("Message: " + msg.getMessageText());
            }
        }
    }
    public static boolean deleteByHash(String hash) {
        Iterator<Messages> iterator = sentMessages.iterator();
        while (iterator.hasNext()) {
            Messages msg = iterator.next();
            if (msg.getMessageHash().equals(hash)) {
                System.out.println("Deleted: " + msg.getMessageText());
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    public static void displayFullReport() {
        for (Messages msg : sentMessages) {
            System.out.println("Message Hash: " + msg.getMessageHash());
            System.out.println("Recipient: " + msg.getRecipient());
            System.out.println("Message: " + msg.getMessageText());
            System.out.println("Message ID: " + msg.getMessageID());
            System.out.println("--------------------------------");
        }
    }
    public static void loadStoredMessages() {
        try (BufferedReader reader = new BufferedReader(new FileReader("storedMessages.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject obj = (JSONObject) new JSONParser().parse(line);
                Messages msg = new Messages(
                        Integer.parseInt(obj.get("MessageNumber").toString()),
                        obj.get("Recipient").toString(),
                        obj.get("MessageText").toString()
                );
                storedMessages.add(msg);
            }
        } catch (Exception e) {
            System.err.println("Error reading stored messages: " + e.getMessage());
        }
    }
}

        
    
        
