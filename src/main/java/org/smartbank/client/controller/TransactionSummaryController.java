package org.smartbank.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;

public class TransactionSummaryController {

    @FXML
    private void handleGoBackClick(MouseEvent event) {
        try {
            System.out.println("Go Back button clicked!");

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
            System.out.println("Close button clicked!");

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
}
