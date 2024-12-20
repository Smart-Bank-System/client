package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;

public class LoginScreenController {

    // Declare the TextFields and other UI elements you want to interact with
    @FXML
    private TextField tcknField;
    @FXML
    private TextField passwordField;

    // Add event handlers for the buttons or other actions
    @FXML
    private void handleLoginClick(MouseEvent event) {
        String tckn = tcknField.getText();
        String password = passwordField.getText();
        System.out.println("TCKN: " + tckn + ", Password: " + password);

        // Add logic for handling login here
        // Example: Validate the TCKN and Password
    }

    // Optionally, add methods for other buttons or actions
}