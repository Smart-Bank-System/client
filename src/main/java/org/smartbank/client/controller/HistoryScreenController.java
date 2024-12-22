package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.smartbank.client.model.Transaction;
import org.smartbank.client.service.TransactionService;
import org.smartbank.client.util.SessionManager;

import java.io.IOException;
import java.util.List;

public class HistoryScreenController {
    @FXML
    private ListView<String> historyListView; // ListView for displaying transactions
    @FXML
    private Button goBackButton;

    private final TransactionService transactionService = TransactionService.getInstance();

    @FXML
    public void initialize() {
        // Access the current user from the SessionManager
        if (SessionManager.getInstance().getCurrentUser() != null) {
            int userId = SessionManager.getInstance().getCurrentUser().getUserId();
            populateTransactionHistory(userId);
        } else {
            System.err.println("No user found in the session.");
        }
    }

    private void populateTransactionHistory(int userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);

        // Clear the ListView before populating
        historyListView.getItems().clear();

        if (transactions.isEmpty()) {
            historyListView.getItems().add("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                String transactionInfo = String.format(
                        "%,.2f TL\n%s\n%s - %s",
                        transaction.getAmount(),
                        transaction.getTransactionType(),
                        transaction.getDate().toLocalTime().toString(),
                        transaction.getDate().toLocalDate().toString()
                );
                historyListView.getItems().add(transactionInfo);
            }
        }
    }

    @FXML
    private void handleGoBackClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/customerHome.fxml"));
            Scene customerHomeScene = new Scene(loader.load());

            // Set the new scene in the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(customerHomeScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load the customer home screen.");
        }
    }
}
