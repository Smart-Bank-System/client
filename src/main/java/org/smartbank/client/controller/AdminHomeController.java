package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
public class AdminHomeController {
    @FXML
    private Label historyLabel;
    @FXML
    private Label newaccount_label;
    @FXML
    private Label request_label;
    @FXML
    public void initialize() {
        URL fontUrlAvenir = getClass().getResource("/org/smartbank/client/fonts/AvenirNext-Bold.ttf");
        if (fontUrlAvenir != null) {
            Font font = Font.loadFont(fontUrlAvenir.toExternalForm(), 120);
            historyLabel.setFont(font);
            newaccount_label.setFont(font);
            request_label.setFont(font);

        }
    }
    @FXML
    private void handleLogoutClick() {
        try {
            System.out.println("Log-out button clicked!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/smartbank/client/loginScreenUser.fxml"));
            Scene loginScene = new Scene(loader.load());

            Stage currentStage = (Stage) historyLabel.getScene().getWindow();
            currentStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load login screen!");
        }
    }
}
