package org.smartbank.client.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginScreenController {

    // Declare the TextFields and other UI elements you want to interact with
    @FXML
    private TextField tcknField;
    @FXML
    private TextField passwordField;

    @FXML
    private Hyperlink createAccountLink;

    // Add event handlers for the buttons or other actions
    @FXML
    private void handleLoginClick(MouseEvent event) {
        String tckn = tcknField.getText();
        String password = passwordField.getText();
        System.out.println("TCKN: " + tckn + ", Password: " + password);

        // Add logic for handling login here
        // Example: Validate the TCKN and Password
    }

    @FXML
    private void handleCreateAccountClick(MouseEvent event) {
        try {
            System.out.println("Create an account clicked!");
            // Load the register screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/registerScreen.fxml"));
            Scene registerScene = new Scene(loader.load());

            // Get the current stage (window) and set the new scene
            Stage currentStage = (Stage) createAccountLink.getScene().getWindow();
            currentStage.setScene(registerScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load register screen!");
        }
    }

}