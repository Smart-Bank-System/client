package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import org.smartbank.client.model.User;
import org.smartbank.client.service.TransactionService;
import org.smartbank.client.service.UserService;
import org.smartbank.client.util.SessionManager;
import javafx.scene.Node;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CustomerHomeController {

    @FXML
    private Label accountBalanceLabel;


    private final UserService userService = UserService.getInstance(); // Singleton UserService
    private final TransactionService transactionService = TransactionService.getInstance(); // Singleton TransactionService

    @FXML
    private TextField depositAmountField;
    @FXML
    private TextField withdrawAmountField;
    @FXML
    private Label accountNumberLabel;

    @FXML
    public void initialize() {

        URL fontUrlAvenir = getClass().getResource("/org/smartbank/client/fonts/AvenirNext-Bold.ttf");
        if (fontUrlAvenir != null) {
            Font font = Font.loadFont(fontUrlAvenir.toExternalForm(), 100);
            accountBalanceLabel.setFont(font);
        }
        // Retrieve the current user from the SessionManager
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        } else {
            System.err.println("No user found in the session.");
        }
    }

    private void updateUI(User currentUser) {
        // Update UI elements with user-specific data
        accountBalanceLabel.setText(String.format("%.2f TL", currentUser.getBalance()));
        accountNumberLabel.setText(currentUser.getAccountNumber());
    }

    @FXML
    private void handleDepositClick(MouseEvent event) {
        try {
            double depositAmount = Double.parseDouble(depositAmountField.getText().trim());
            User currentUser = SessionManager.getInstance().getCurrentUser();

            if (currentUser != null && depositAmount > 0) {
                // Use UserService to perform the deposit operation
                boolean success = userService.deposit(currentUser.getUserId(), depositAmount);

                if (success) {
                    // Update the current user's balance after the successful deposit
                    currentUser.setBalance(currentUser.getBalance() + depositAmount);
                    updateUI(currentUser);
                    System.out.println("Deposited: " + depositAmount + " TL");
                } else {
                    System.err.println("Deposit failed. Please try again.");
                }
            } else {
                System.err.println("Invalid Amount: Please enter a positive deposit amount.");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Invalid Input: Please enter a valid number.");
        }
    }

    @FXML
    private void handleWithdrawClick(MouseEvent event) {
        try {
            double withdrawAmount = Double.parseDouble(withdrawAmountField.getText().trim());
            User currentUser = SessionManager.getInstance().getCurrentUser();

            if (currentUser != null && withdrawAmount > 0) {
                // Use UserService to perform the withdrawal operation
                boolean success = userService.withdraw(currentUser.getUserId(), withdrawAmount);

                if (success) {
                    // Update the current user's balance after the successful withdrawal
                    currentUser.setBalance(currentUser.getBalance() - withdrawAmount);
                    updateUI(currentUser);
                    System.out.println("Withdrew: " + withdrawAmount + " TL");
                } else {
                    System.err.println("Withdrawal failed: Insufficient balance or invalid amount.");
                }
            } else {
                System.err.println("Invalid Amount: Please enter a positive withdrawal amount.");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Invalid Input: Please enter a valid number.");
        }
    }

    @FXML
    private void handleLogoutClick(MouseEvent event) {
        try {
            System.out.println("Logging out...");

            // Clear the session
            SessionManager.getInstance().clearSession();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/loginScreenUser.fxml"));
            Scene loginScene = new Scene(loader.load());

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load login screen.");
        }
    }

    @FXML
    private void handleHistoryClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/historyScreen.fxml"));
            Scene historyScene = new Scene(loader.load());

            // Automatically use the current user from the SessionManager
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(historyScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load history screen.");
        }
    }

    @FXML
    private void handleTransferClick(MouseEvent event) {
        try {
            System.out.println("Transfer Button clicked");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/transferScreen.fxml"));
            Scene transferScene = new Scene(loader.load());

            // Automatically use the current user from the SessionManager
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(transferScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load transfer screen.");
        }
    }
}
