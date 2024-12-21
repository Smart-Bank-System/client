package org.smartbank.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField fullNameField; // Full Name input
    @FXML
    private TextField tcknField;     // TCKN input

    @FXML
    private void handleRegisterButtonClick(ActionEvent event) {
        String fullName = fullNameField.getText().trim();
        String tckn = tcknField.getText().trim();

        // Validate inputs
        if (validateFullName(fullName) && validateTCKN(tckn)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/registerScreen2.fxml"));
                Parent registerScreen2Root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(new Scene(registerScreen2Root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Invalid Input", "Please enter a valid Full Name and TCKN.");
        }
    }
    private boolean validateFullName(String fullName) {
        return fullName.matches("[a-zA-Z\\s]+") && !fullName.isBlank();
    }
    private boolean validateTCKN(String tckn) {
        return tckn.matches("\\d{11}");
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void handlePreviousButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/loginScreenUser.fxml"));
            Parent loginRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(loginRoot));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handlePreviousButtonClick2(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/registerScreen.fxml"));
            Parent loginRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(loginRoot));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
