package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import org.smartbank.client.services.AuthService;
import org.smartbank.client.model.User;

import java.io.IOException;

public class LoginScreenUserController {

    private final AuthService authService = new AuthService(); // Instantiate the AuthService

    @FXML
    private TextField tcknField;
    @FXML
    private TextField passwordField;
    @FXML
    private Hyperlink createAccountLink;

    @FXML
    private void handleGoBackClick(MouseEvent event) {
        try {
            System.out.println("Go Back button clicked!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/firstScreen.fxml"));
            Scene firstScreenScene = new Scene(loader.load());

            // Get the controller and pass the primary stage
            FirstScreenController controller = loader.getController();
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            controller.setPrimaryStage(currentStage); // Pass the stage to avoid NullPointerException

            currentStage.setScene(firstScreenScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load the first screen!");
        }
    }

    @FXML
    private void handleLoginClick(MouseEvent event) {
        System.out.println("LOGIN CLICKED ********");
        String tckn = tcknField.getText();
        String password = passwordField.getText();

        if (tckn.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter your TCKN and password.");
            return;
        }

        User user = authService.login(tckn, password);
        if (user != null) {
            // Login successful
            try {
                System.out.println("Login successful for user: " + user);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/customerHome.fxml"));
                Scene customerHomeScene = new Scene(loader.load());

                // Pass the user object to the next screen
                CustomerHomeController controller = loader.getController();
                controller.initializeUser(user);

                Stage currentStage = (Stage) tcknField.getScene().getWindow();
                currentStage.setScene(customerHomeScene);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load customer home screen.");
            }
        } else {
            // Login failed
            showAlert("Error", "Invalid TCKN or password. Please try again.");
        }
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
