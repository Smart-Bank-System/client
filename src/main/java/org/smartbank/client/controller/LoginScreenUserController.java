package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginScreenUserController {


    // Declare the TextFields and other UI elements you want to interact with
    @FXML
    private TextField tcknField;
    @FXML
    private TextField passwordField;

    @FXML
    private Hyperlink createAccountLink;


    @FXML
    private void handleGoBackClick(MouseEvent event) {
        try {
            System.out.println("Go Back button clicked!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/firstScreen.fxml"));
            Scene firstScreenScene = new Scene(loader.load());

            // Get the controller and pass primaryStage
            FirstScreenController controller = loader.getController();
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            controller.setPrimaryStage(currentStage);  // Pass the stage to avoid NullPointerException

            currentStage.setScene(firstScreenScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load first screen!");
        }
    }



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



    @FXML
    private void handleCreateAccountClick(MouseEvent event) {
        try {
            System.out.println("Create an account clicked!");
            // Load the register screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/registerScreen.fxml"));
            Scene registerScene = new Scene(loader.load());

            // Get the current stage (window) and set the new scene
            Stage currentStage = (Stage) createAccountLink.getScene().getWindow();
            currentStage.setScene(registerScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load register screen!");
        }
    }

}