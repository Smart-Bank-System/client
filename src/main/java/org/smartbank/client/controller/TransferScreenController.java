package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import org.smartbank.client.model.User;

import java.io.IOException;

public class TransferScreenController {

    @FXML
    private TextField accountNumberField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField transferAmountField;

    private User currentUser; // To store the user object

    public void initializeUser(User user) {
        this.currentUser = user;
        System.out.println("User in History screen: " + user);
    }
    @FXML
    private void handleGoBackClick(MouseEvent event) {
        try {
            System.out.println("Go Back button clicked!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/customerHome.fxml"));
            Scene customerHomeScene = new Scene(loader.load());

            // Get the controller of CustomerHome screen and pass the current user
            CustomerHomeController customerHomeController = loader.getController();
            customerHomeController.initializeUser(currentUser);  // Pass currentUser to CustomerHomeController

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(customerHomeScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load customer home screen.");
        }
    }



    @FXML
    private void handleTransferClick(MouseEvent event) {
        try {
            String accountNumber = accountNumberField.getText().trim();
            String fullName = fullNameField.getText().trim();
            double transferAmount = Double.parseDouble(transferAmountField.getText().trim());

            // Basic validation
            if (accountNumber.isEmpty() || fullName.isEmpty() || transferAmount <= 0) {
                System.err.println("Invalid Input: Please fill all fields with valid information.");
            } else {
                // Transfer logic (e.g., update database or balance)
                System.out.println("Transfer of " + transferAmount + " TL to account " + accountNumber + " for " + fullName);

                // After successful transfer, load the transaction summary screen
                System.out.println("Transfer successful. Loading transaction summary screen...");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/transactionSummary.fxml"));
                Scene transactionSummaryScene = new Scene(loader.load());

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(transactionSummaryScene);
                currentStage.show();
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid Amount: Please enter a valid amount.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load transaction summary screen.");
        }
    }
}