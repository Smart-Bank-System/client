package org.smartbank.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.smartbank.client.controller.FirstScreenController;

import java.io.IOException;

public class BankingApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the first screen FXML
        FXMLLoader fxmlLoader = new FXMLLoader(BankingApp.class.getResource("firstScreen.fxml"));

        // Load the scene
        Scene scene = new Scene(fxmlLoader.load());

        // Retrieve the controller and pass the primaryStage to it
        FirstScreenController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);

        // Set the stage properties
        primaryStage.setTitle("Welcome Screen!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}