package org.smartbank.client.model;

public class Admin extends User {

    public Admin(String accountNumber, String userId) {
        super(accountNumber, 0.0, userId);  // Admin does not have a balance, so set it to 0
    }

    // Admin-specific functionality
    public void viewAllUsers() {
        // Logic to view all users' information
        System.out.println("Displaying all user accounts...");
    }

    public void manageTransactions() {
        // Logic to view, modify, or delete transactions
        System.out.println("Managing transactions...");
    }

    // Admin may not need deposit/withdraw methods
    @Override
    public void deposit(double amount) {
        System.out.println("Admin cannot perform deposit operation.");
    }

    @Override
    public boolean withdraw(double amount) {
        System.out.println("Admin cannot perform withdrawal operation.");
        return false;
    }

    // Admin may also override toString() method if needed for customized display
    @Override
    public String toString() {
        return "Admin [Account Number: " + getAccountNumber() + ", User ID: " + getUserId() + "]";
    }
}