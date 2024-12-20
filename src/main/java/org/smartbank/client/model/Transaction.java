package org.smartbank.client.model;

import java.time.LocalDateTime;

public class Transaction {

    private String transactionId;  // Unique transaction identifier
    private String transactionType;  // Type of transaction (deposit, withdrawal, transfer)
    private double amount;           // Amount involved in the transaction
    private LocalDateTime date;      // Date and time of the transaction
    private String fromAccount;     // The account initiating the transaction (for transfers)
    private String toAccount;       // The account receiving the transaction (for transfers)

    // Constructor for deposit or withdrawal transaction
    public Transaction(String transactionId, String transactionType, double amount, String fromAccount) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.fromAccount = fromAccount;
        this.toAccount = null;  // No recipient for deposit/withdrawal
    }

    // Constructor for transfer transaction
    public Transaction(String transactionId, String transactionType, double amount, String fromAccount, String toAccount) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    // Getters and setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    // Method to display transaction details (for example, in transaction history)
    public void displayTransactionInfo() {
        String transactionInfo = "Transaction ID: " + transactionId + "\n" +
                "Transaction Type: " + transactionType + "\n" +
                "Amount: " + amount + "\n" +
                "Date: " + date + "\n" +
                "From Account: " + fromAccount;

        if (transactionType.equalsIgnoreCase("transfer")) {
            transactionInfo += "\nTo Account: " + toAccount;
        }

        System.out.println(transactionInfo);
    }

    // ToString method to represent the transaction
    @Override
    public String toString() {
        return "Transaction [ID: " + transactionId + ", Type: " + transactionType + ", Amount: " + amount +
                ", Date: " + date + ", From Account: " + fromAccount + ", To Account: " + toAccount + "]";
    }
}