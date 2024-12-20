package org.smartbank.client.model;

public class User {

    private String accountNumber;  // Unique account identifier
    private double balance;        // Account balance
    private String userId;         // User ID

    // Constructor for initializing the user account
    public User(String accountNumber, double initialBalance, String userId) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.userId = userId;
    }

    // Getters and setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Common methods for both user and admin
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds.");
            return false;
        }
        balance -= amount;
        System.out.println("Withdrew: " + amount + ". New balance: " + balance);
        return true;
    }

    // ToString method to represent the user info
    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", User ID: " + userId + ", Balance: " + balance;
    }
}