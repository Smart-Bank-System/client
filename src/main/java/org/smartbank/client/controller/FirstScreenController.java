package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import java.net.URL;

public class FirstScreenController {
    @FXML
    private Label welcomeLabel;

    @FXML
    private Label adminLabel;
    @FXML
    private Label userLabel;
    @FXML
    public void initialize() {
        URL fontUrlAlegreya = getClass().getResource("/org/smartbank/client/fonts/Alegreya-VariableFont_wght.ttf");
        if (fontUrlAlegreya != null) {
            Font font = Font.loadFont(fontUrlAlegreya.toExternalForm(), 120);
            welcomeLabel.setFont(font);
        }

        URL fontUrlAvenir = getClass().getResource("/org/smartbank/client/fonts/AvenirNext-Bold.ttf");
        if (fontUrlAvenir != null) {
            Font font = Font.loadFont(fontUrlAvenir.toExternalForm(), 40);
            userLabel.setFont(font);
            adminLabel.setFont(font);
        }
    }
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