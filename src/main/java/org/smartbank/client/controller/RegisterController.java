package org.smartbank.client.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.smartbank.client.service.AuthService;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField fullNameField; // Full Name input

    @FXML
    private TextField tcknField; // TCKN input

    @FXML
    private PasswordField passwordField; // Password input

    @FXML
    private ChoiceBox<String> preferredBankChoiceBox; // Preferred Bank Selection

    @FXML
    private Label messageLabel; // Message display label

    private final AuthService authService = new AuthService();

    @FXML
    public void initialize() {
        if (preferredBankChoiceBox != null) {
            preferredBankChoiceBox.getItems().addAll("Garanti BBVA", "Fibabanka", "İş Bankası");
        }
    }

    @FXML
    private void handleCompleteRegistrationClick(ActionEvent event) {
        String fullname = (this.fullName != null) ? this.fullName : null;
        String tckn = (this.tckn != null) ? this.tckn : null;
        String password = (passwordField != null) ? passwordField.getText().trim() : null;
        String preferredBank = (preferredBankChoiceBox != null) ? preferredBankChoiceBox.getValue() : null;

        // Debug output
        System.out.println("Full Name: " + fullname);
        System.out.println("TCKN: " + tckn);
        System.out.println("Password: " + password);
        System.out.println("Preferred Bank: " + preferredBank);

        // Validate input fields
        if (fullname == null || fullname.isEmpty() || tckn == null || tckn.isEmpty() ||
                password == null || password.isEmpty() || preferredBank == null || preferredBank.isEmpty()) {
            showMessage("Please fill in all fields.", "error");
            return;
        }

        // Validate TCKN and Password
        if (!validateTCKN(tckn)) {
            showMessage("Invalid TCKN. Please enter a valid 11-digit TCKN.", "error");
            return;
        }

        if (!validatePassword(password)) {
            showMessage("Invalid password. Password must be at least 6 characters long, include a number, and an uppercase letter.", "error");
            return;
        }

        // Call the registration service
        if (authService.register(fullname, tckn, password, preferredBank)) {
            showMessage("Registration successful! Awaiting admin approval.", "success");
            clearFields();
        } else {
            showMessage("Registration failed. Please try again.", "error");
        }
    }


    @FXML
    private void handleNextButtonClick(ActionEvent event) {
        String fullName = fullNameField.getText().trim();
        String tckn = tcknField.getText().trim();

        // Validate Full Name
        if (!validateFullName(fullName)) {
            showMessage("Invalid Full Name. Please enter a valid name.", "error");
            return;
        }

        // Validate TCKN
        if (!validateTCKN(tckn)) {
            showMessage("Invalid TCKN. Please enter a valid 11-digit TCKN.", "error");
            return;
        }

        try {
            // Load the second registration screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/registerScreen2.fxml"));
            Parent root = loader.load();

            // Pass data to the next controller
            RegisterController controller = loader.getController();
            controller.setFullNameAndTCKN(fullName, tckn);

            // Switch scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error loading the next screen.", "error");
        }
    }


    private String fullName;
    private String tckn;

    public void setFullNameAndTCKN(String fullName, String tckn) {
        this.fullName = fullName;
        this.tckn = tckn;
        if (fullNameField != null) fullNameField.setText(fullName);
        if (tcknField != null) tcknField.setText(tckn);
    }



    @FXML
    private void handlePreviousButtonClick(ActionEvent event) {
        try {
            Parent loginScreenRoot = FXMLLoader.load(getClass().getResource("/org/smartbank/client/loginScreenUser.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginScreenRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error", "Failed to load login screen.");
        }
    }

    private void showMessage(String message, String type) {
        if (messageLabel != null) {
            messageLabel.setText(message);
            if ("success".equalsIgnoreCase(type)) {
                messageLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else {
                messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        } else {
            System.err.println("Message label is null!");
        }
    }


    private void clearFields() {
        if (fullNameField != null) fullNameField.clear();
        if (tcknField != null) tcknField.clear();
        if (passwordField != null) passwordField.clear();
        if (preferredBankChoiceBox != null) preferredBankChoiceBox.getSelectionModel().clearSelection();
    }


    private boolean validateFullName(String fullName) {
        // Null veya boş kontrolü
        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }

        // Türkçe karakterleri destekleyen, bir veya birden fazla kelimeyi kabul eden regex
        return fullName.matches("^[A-Za-zÇĞİÖŞÜçğıöşü]+( [A-Za-zÇĞİÖŞÜçğıöşü]+)*$");
    }

    private boolean validateTCKN(String tckn) {
        return tckn.matches("\\d{11}");
    }

    private boolean validatePassword(String password) {
        return password.length() >= 6 && password.matches(".*\\d.*") && password.matches(".*[A-Z].*");
    }

}
