package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.smartbank.client.model.Admin;
import org.smartbank.client.model.Transaction;
import org.smartbank.client.model.User;
import org.smartbank.client.service.AdminService;

import java.io.IOException;
import java.util.List;

public class AdminHomeController {

    @FXML
    private VBox historyVBox;

    @FXML
    private VBox newRequestsVBox;

    @FXML
    private Label historyLabel;

    @FXML
    private Label newaccountLabel;

    @FXML
    private Label requestLabel;

    @FXML
    private Button logOutButton;

    private Admin admin;
    private final AdminService adminService = AdminService.getInstance();

    public void initialize() {
        // Initialize any necessary values or components here
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
        loadTransactionHistory();
        loadNewAccountRequests();
    }

    private void loadTransactionHistory() {
        List<Transaction> transactions = adminService.getTransactionHistory(admin);
        historyVBox.getChildren().clear();

        for (Transaction transaction : transactions) {
            HBox transactionItem = createTransactionItem(transaction);
            historyVBox.getChildren().add(transactionItem);
        }
    }

    private void loadNewAccountRequests() {
        List<User> pendingUsers = adminService.getNewAccountRequests(admin);
        newRequestsVBox.getChildren().clear();

        for (User user : pendingUsers) {
            HBox requestItem = createRequestItem(user);
            newRequestsVBox.getChildren().add(requestItem);
        }
    }

    private HBox createTransactionItem(Transaction transaction) {
        HBox transactionItem = new HBox();
        transactionItem.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-border-color: lightgray; -fx-border-radius: 5; -fx-background-radius: 5; -fx-spacing: 10;");
        transactionItem.setPrefHeight(50);

        Label amountLabel = new Label(String.format("%.2f TL", transaction.getAmount()));
        amountLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextFlow detailsFlow = new TextFlow(
                new Text("From: " + transaction.getFromUserId() + "\n"),
                new Text("To: " + transaction.getToUserId() + "\n"),
                new Text("Date: " + transaction.getDate())
        );
        detailsFlow.setTextAlignment(TextAlignment.LEFT);
        detailsFlow.setStyle("-fx-font-size: 14px;");

        transactionItem.getChildren().addAll(amountLabel, detailsFlow);
        return transactionItem;
    }

    private HBox createRequestItem(User user) {
        HBox requestItem = new HBox();
        requestItem.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-border-color: lightgray; -fx-border-radius: 5; -fx-background-radius: 5; -fx-spacing: 10;");
        requestItem.setPrefHeight(50);

        Label userDetailsLabel = new Label(
                String.format("User ID: %d\nFull Name: %s\nTCKN: %s\nAccount Number: %s",
                        user.getUserId(), user.getFullname(), user.getTckn(), user.getAccountNumber())
        );
        userDetailsLabel.setStyle("-fx-font-size: 14px;");

        Button approveButton = new Button("Approve");
        approveButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-border-radius: 5; -fx-padding: 5;");
        approveButton.setOnAction(e -> handleApproveRequest(user));

        Button denyButton = new Button("Deny");
        denyButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-border-radius: 5; -fx-padding: 5;");
        denyButton.setOnAction(e -> handleDenyRequest(user));

        requestItem.getChildren().addAll(userDetailsLabel, approveButton, denyButton);
        return requestItem;
    }

    private void handleApproveRequest(User user) {
        adminService.updateUserStatus(user.getUserId(), "CONFIRMED");
        showAlert("Success", "User approved successfully.", Alert.AlertType.INFORMATION);
        loadNewAccountRequests();
    }

    private void handleDenyRequest(User user) {
        adminService.updateUserStatus(user.getUserId(), "DENIED");
        showAlert("Success", "User denied successfully.", Alert.AlertType.INFORMATION);
        loadNewAccountRequests();
    }

    @FXML
    private void handleLogoutClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/loginScreenAdmin.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage currentStage = (Stage) logOutButton.getScene().getWindow();
            currentStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load login screen.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
