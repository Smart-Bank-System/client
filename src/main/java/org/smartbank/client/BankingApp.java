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

        FXMLLoader fxmlLoader = new FXMLLoader(BankingApp.class.getResource("firstScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        FirstScreenController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Welcome Screen!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}