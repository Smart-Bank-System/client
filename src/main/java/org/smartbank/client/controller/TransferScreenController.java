package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.smartbank.client.model.Transaction;
import org.smartbank.client.model.User;
import org.smartbank.client.service.TransactionService;
import org.smartbank.client.service.UserService;
import org.smartbank.client.util.SessionManager;

import java.io.IOException;
import java.time.LocalDateTime;

public class TransferScreenController {

    @FXML
    private TextField accountNumberField;
    @FXML
    private TextField transferAmountField;

    private final TransactionService transactionService = TransactionService.getInstance(); // Singleton TransactionService
    private final UserService userService = UserService.getInstance(); // Singleton UserService

    @FXML
    public void initialize() {
        // Ensure a user is logged in
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser == null) {
            System.err.println("No user found in the session. Redirecting to login...");
            redirectToLogin();
        }
    }

    @FXML
    private void handleGoBackClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/customerHome.fxml"));
            Scene customerHomeScene = new Scene(loader.load());

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
            String transferAmountText = transferAmountField.getText().trim();

            // Validate input
            if (accountNumber.isEmpty() || transferAmountText.isEmpty()) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }

            double transferAmount;
            try {
                transferAmount = Double.parseDouble(transferAmountText);
                if (transferAmount <= 0) {
                    showAlert("Error", "Please enter a valid transfer amount.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid numeric transfer amount.");
                return;
            }

            User currentUser = SessionManager.getInstance().getCurrentUser();
            if (currentUser == null) {
                showAlert("Error", "No user is logged in.");
                redirectToLogin();
                return;
            }

            // Fetch recipient user by account number
            User recipient = userService.getUserByAccountNumber(accountNumber);
            if (recipient == null) {
                showAlert("Error", "Recipient account not found.");
                return;
            }

            // Check for preferred bank mismatch
            if (!recipient.getPreferredBank().equalsIgnoreCase(currentUser.getPreferredBank())) {
                showAlert("Error", "Recipient's preferred bank does not match your preferred bank.");
                return;
            }

            // Perform the transfer
            boolean transferSuccessful = userService.transfer(currentUser.getUserId(), recipient.getUserId(), transferAmount);

            if (transferSuccessful) {
                // Create a new Transaction object
                Transaction recentTransaction = new Transaction(
                        "TRANSFER",
                        transferAmount,
                        currentUser.getUserId(), // From User ID
                        recipient.getUserId(),   // To User ID
                        LocalDateTime.now()
                );

                // Save the recent transaction to the SessionManager
                SessionManager.getInstance().setRecentTransaction(recentTransaction);

                // Refresh the current user's data in the session
                User updatedUser = userService.getUserById(currentUser.getUserId());
                if (updatedUser != null) {
                    SessionManager.getInstance().setCurrentUser(updatedUser);
                }

                showAlert("Success", "Transfer completed successfully!");

                // Redirect to transaction summary
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/transactionSummary.fxml"));
                Scene transactionSummaryScene = new Scene(loader.load());

                // Pass account numbers to TransactionSummaryController
                TransactionSummaryController controller = loader.getController();
                controller.setAccountNumbers(
                        currentUser.getAccountNumber(),
                        recipient.getAccountNumber()
                );

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(transactionSummaryScene);
                currentStage.show();

            } else {
                showAlert("Error", "Transfer failed. Please try again.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the transaction summary screen.");
        }
    }

    private void redirectToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/loginScreenUser.fxml"));
            Scene loginScene = new Scene(loader.load());

            Stage currentStage = (Stage) accountNumberField.getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to redirect to login screen.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
