package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import org.smartbank.client.service.AuthService;
import org.smartbank.client.model.Admin;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginScreenAdminController {

    @FXML
    private TextField tcknField;

    @FXML
    private TextField passwordField;
    private final AuthService authService = new AuthService();

    @FXML
    private void handleLoginClick(MouseEvent event) {
        String tckn = tcknField.getText().trim(); // Changed to tcknField
        String password = passwordField.getText().trim(); // Changed to passwordField

        if (tckn.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter your TCKN and password.");
            return;
        }
        Admin admin = authService.loginAdmin(tckn, password);
        if (admin != null) {
            try {
                System.out.println("Login successful for admin: " + admin);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/adminHomeScreen.fxml"));
                Scene adminHomeScene = new Scene(loader.load());

                Stage currentStage = (Stage) tcknField.getScene().getWindow(); // Use tcknField instead of tcknLabel
                currentStage.setScene(adminHomeScene);

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load admin home screen.");
            }
        } else {
            showAlert("Error", "Invalid TCKN or password. Please try again.");
        }
    }



    @FXML
    private void handleGoBackClick(MouseEvent event) {
        try {
            System.out.println("Go Back button clicked!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/firstScreen.fxml"));
            Scene firstScreenScene = new Scene(loader.load());

            // Pass primaryStage back to FirstScreenController
            FirstScreenController controller = loader.getController();
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            controller.setPrimaryStage(currentStage);  // Ensure the stage is passed

            currentStage.setScene(firstScreenScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load first screen!");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*
    @FXML
    private void handleLoginClick(MouseEvent event) {
        try {
            System.out.println("Login button clicked!");

            // Load the customer home screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/customerHome.fxml"));
            Scene customerHomeScene = new Scene(loader.load());

            // Get the current stage from any node (like tcknField)
            Stage currentStage = (Stage) tcknField.getScene().getWindow();
            currentStage.setScene(customerHomeScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load customer home screen!");
        }
    }

     */


}