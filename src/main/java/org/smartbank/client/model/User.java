package org.smartbank.client.model;

public class User {

    private String accountNumber;
    private double balance;
    private String userId;

    // Constructor for initializing the user account
    public User(String accountNumber, double initialBalance, String userId) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.userId = userId;
    }

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

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", User ID: " + userId + ", Balance: " + balance;
    }
}