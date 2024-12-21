package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginScreenAdminController {


    // Declare the TextFields and other UI elements you want to interact with
    @FXML
    private TextField tcknField;
    @FXML
    private TextField passwordField;


    @FXML
    private void handleGoBackClick(MouseEvent event) {
        try {
            System.out.println("Go Back button clicked!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/firstScreen.fxml"));
            Scene firstScreenScene = new Scene(loader.load());

            // Pass primaryStage back to FirstScreenController
            FirstScreenController controller = loader.getController();
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            controller.setPrimaryStage(currentStage);  // Ensure the stage is passed

            currentStage.setScene(firstScreenScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load first screen!");
        }
    }


    /*
    @FXML
    private void handleLoginClick(MouseEvent event) {
        try {
            System.out.println("Login button clicked!");

            // Load the customer home screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/customerHome.fxml"));
            Scene customerHomeScene = new Scene(loader.load());

            // Get the current stage from any node (like tcknField)
            Stage currentStage = (Stage) tcknField.getScene().getWindow();
            currentStage.setScene(customerHomeScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load customer home screen!");
        }
    }

     */


}