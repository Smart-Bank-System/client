package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class FirstScreenController {

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void handleAdminClick(MouseEvent event) throws IOException {
        System.out.println("Admin VBox clicked!");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/loginScreenAdmin.fxml"));
        Scene adminScene = new Scene(loader.load());

        primaryStage.setScene(adminScene);
        primaryStage.show();
    }

    // Handle User click event (example)
    @FXML
    private void handleUserClick(MouseEvent event) throws IOException {
        System.out.println("User VBox clicked!");

        // Load the User login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/loginScreenUser.fxml"));
        Scene userScene = new Scene(loader.load());

        // Set the new scene on the primary stage
        primaryStage.setScene(userScene);
        primaryStage.show();
    }
}