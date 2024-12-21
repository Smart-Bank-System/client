package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import org.smartbank.client.model.User;

import java.io.IOException;

public class HistoryScreenController {

    private User currentUser; // To store the user object

    @FXML
    private ListView<String> historyListView;

    // Method to initialize user and update UI
    public void initializeUser(User user) {
        this.currentUser = user;
        System.out.println("User in History screen: " + user);

        // Update the historyListView or other UI elements with user-specific data if needed
        // e.g., historyListView.getItems().add(currentUser.getTransactionHistory());
    }

    @FXML
    private void handleGoBackClick(MouseEvent event) {
        try {
            System.out.println("Go Back button clicked!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/customerHome.fxml"));
            Scene customerHomeScene = new Scene(loader.load());

            CustomerHomeController customerHomeController = loader.getController();
            customerHomeController.initializeUser(currentUser); // Pass the user object to CustomerHomeController

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(customerHomeScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load customer home screen.");
        }
    }
}
