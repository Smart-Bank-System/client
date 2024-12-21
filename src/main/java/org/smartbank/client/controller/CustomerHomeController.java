package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.smartbank.client.model.User;

public class CustomerHomeController {
    private User currentUser;

    @FXML
    private Label accountBalanceLabel; // Link to Account Balance Label
    @FXML
    private Label accountNumberLabel; // Link to Account Number Label

    // Method to initialize user and update UI
    public void initializeUser(User user) {
        this.currentUser = user;
        System.out.println("Logged in user: " + user);

        // Update UI elements with user-specific data
        accountBalanceLabel.setText(String.format("%.2f TL", currentUser.getBalance()));
        accountNumberLabel.setText(currentUser.getAccountNumber());
    }
}
