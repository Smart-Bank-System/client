package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.smartbank.client.model.Transaction;

public class HistoryController {

    @FXML
    private ListView<HBox> historyListView; // ListView to display transactions

    /**
     * Adds a new transaction to the ListView.
     *
     * @param transaction The transaction to add.
     */
    public void addTransaction(Transaction transaction) {

        HBox transactionBox = new HBox();
        transactionBox.setSpacing(20);

        Label transactionIdLabel = new Label("ID: " + transaction.getTransactionId());
        Label transactionTypeLabel = new Label("Type: " + transaction.getTransactionType());
        Label amountLabel = new Label("Amount: " + transaction.getAmount() + " TL");
        Label dateLabel = new Label("Date: " + transaction.getDate().toString());
        Label fromLabel = new Label("From: " + transaction.getFromAccount());

        // Optional: Add recipient for transfer transactions
        if ("transfer".equalsIgnoreCase(transaction.getTransactionType()) && transaction.getToAccount() != null) {
            Label toLabel = new Label("To: " + transaction.getToAccount());
            transactionBox.getChildren().add(toLabel);
        }

        transactionBox.getChildren().addAll(transactionIdLabel, transactionTypeLabel, amountLabel, dateLabel, fromLabel);

        historyListView.getItems().add(transactionBox);
    }

    @FXML
    public void initialize() {
        // Optionally populate with sample transactions (for testing purposes)
        addTransaction(new Transaction("1", "deposit", 1000.0, "123456789"));
        addTransaction(new Transaction("2", "withdrawal", 500.0, "987654321"));
        addTransaction(new Transaction("3", "transfer", 200.0, "123456789", "987654321"));
    }

    /**
     * Call this method whenever a transaction occurs.
     * @param transaction The transaction to add.
     */
    public void onTransactionOccurred(Transaction transaction) {
        addTransaction(transaction);
    }

}
