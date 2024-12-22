package org.smartbank.client.model;

import javafx.beans.property.*;

public class User {
    // Properties for JavaFX binding
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final StringProperty fullname = new SimpleStringProperty();
    private final StringProperty tckn = new SimpleStringProperty();
    private final StringProperty accountNumber = new SimpleStringProperty();
    private final DoubleProperty balance = new SimpleDoubleProperty();
    private final StringProperty preferredBank = new SimpleStringProperty();

    // Constructor
    public User(int userId, String fullname, String tckn, String accountNumber, double balance, String preferredBank) {
        this.userId.set(userId);
        this.fullname.set(fullname);
        this.tckn.set(tckn);
        this.accountNumber.set(accountNumber);
        this.balance.set(balance);
        this.preferredBank.set(preferredBank);
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

    public StringProperty fullnameProperty() {
        return fullname;
    }

    public String getFullname() {
        return fullname.get();
    }

    public void setFullname(String fullname) {
        this.fullname.set(fullname);
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

    public StringProperty preferredBankProperty() {
        return preferredBank;
    }

    public String getPreferredBank() {
        return preferredBank.get();
    }

    public void setPreferredBank(String preferredBank) {
        this.preferredBank.set(preferredBank);
    }

    // Override toString for easier debugging and logging
    @Override
    public String toString() {
        return "User{" +
                "userId=" + getUserId() +
                ", fullname='" + getFullname() + '\'' +
                ", tckn='" + getTckn() + '\'' +
                ", accountNumber='" + getAccountNumber() + '\'' +
                ", balance=" + getBalance() +
                ", preferredBank='" + getPreferredBank() + '\'' +
                '}';
    }
}
