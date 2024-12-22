package org.smartbank.client.model;

import javafx.beans.property.*;

public class User {
    private IntegerProperty userId;          // Unique user ID (auto-incremented in the database)
    private StringProperty tckn;            // Türkiye Cumhuriyeti Kimlik Numarası
    private StringProperty accountNumber;   // Bank account number
    private DoubleProperty balance;         // Account balance

    public User(int userId, String tckn, String accountNumber, double balance) {
        this.userId = new SimpleIntegerProperty(userId);
        this.tckn = new SimpleStringProperty(tckn);
        this.accountNumber = new SimpleStringProperty(accountNumber);
        this.balance = new SimpleDoubleProperty(balance);
    }

    // Getters and Setters for JavaFX Properties
    public IntegerProperty userIdProperty() {
        return userId;
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public StringProperty tcknProperty() {
        return tckn;
    }

    public String getTckn() {
        return tckn.get();
    }

    public void setTckn(String tckn) {
        this.tckn.set(tckn);
    }

    public StringProperty accountNumberProperty() {
        return accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber.get();
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber.set(accountNumber);
    }

    public DoubleProperty balanceProperty() {
        return balance;
    }

    public double getBalance() {
        return balance.get();
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + getUserId() +
                ", tckn='" + getTckn() + '\'' +
                ", accountNumber='" + getAccountNumber() + '\'' +
                ", balance=" + getBalance() +
                '}';
    }
}
