package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.Node;
import java.io.IOException;

public class CustomerHomeController {

    @FXML
    private TextField depositAmountField;

    @FXML
    private TextField withdrawAmountField;

    @FXML
    private Label accountBalanceLabel;

    private double accountBalance = 127869.23; // Example initial balance

    @FXML
    private void handleDepositClick(MouseEvent event) {
        try {
            double depositAmount = Double.parseDouble(depositAmountField.getText().trim());

            if (depositAmount > 0) {
                accountBalance += depositAmount;
                updateBalance();
                System.out.println("Deposited: " + depositAmount + " TL");
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

            if (withdrawAmount > 0 && withdrawAmount <= accountBalance) {
                accountBalance -= withdrawAmount;
                updateBalance();
                System.out.println("Withdrew: " + withdrawAmount + " TL");
            } else {
                System.err.println("Invalid Transaction: Insufficient balance or invalid amount.");
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
            System.out.println("History Button clicked");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/historyScreen.fxml"));
            Scene historyScene = new Scene(loader.load());

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

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(transferScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load transfer screen.");
        }
    }

    private void updateBalance() {
        accountBalanceLabel.setText(String.format("%.2f TL", accountBalance));
    }


}