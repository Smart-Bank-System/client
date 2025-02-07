package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.smartbank.client.model.Transaction;
import org.smartbank.client.util.SessionManager;

import java.io.IOException;

public class TransactionSummaryController {

    @FXML
    private Label transactionAmountLabel;

    @FXML
    private Label transactionDateLabel;

    @FXML
    private Label senderAccountNumberLabel;

    @FXML
    private Label recipientAccountNumberLabel;

    @FXML
    public void initialize() {
        // Retrieve the recent transaction from SessionManager
        Transaction recentTransaction = SessionManager.getInstance().getRecentTransaction();

        if (recentTransaction != null) {
            // Update the UI with transaction details
            transactionAmountLabel.setText(String.format("%.2f TL", recentTransaction.getAmount()));
            transactionDateLabel.setText(String.format("%s - %s",
                    recentTransaction.getDate().toLocalTime().toString(),
                    recentTransaction.getDate().toLocalDate().toString()));
            senderAccountNumberLabel.setText(recentTransaction.getFromUserId() != null
                    ? "Account: " + recentTransaction.getFromUserId()
                    : "N/A");
            recipientAccountNumberLabel.setText(recentTransaction.getToUserId() != null
                    ? "Account: " + recentTransaction.getToUserId()
                    : "N/A");
        } else {
            System.err.println("No recent transaction found in the session.");
        }
    }

    @FXML
    private void handleGoBackClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/transferScreen.fxml"));
            Scene transferScreenScene = new Scene(loader.load());

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(transferScreenScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load transfer screen.");
        }
    }

    @FXML
    private void handleCloseClick(MouseEvent event) {
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

    public void setAccountNumbers(String senderAccountNumber, String recipientAccountNumber) {
        senderAccountNumberLabel.setText("Account: " + senderAccountNumber);
        recipientAccountNumberLabel.setText("Account: " + recipientAccountNumber);
    }
}
