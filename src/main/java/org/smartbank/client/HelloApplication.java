package org.smartbank.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Font customFont = Font.loadFont(
                getClass().getResourceAsStream("/org.smartbank.client/fonts/Alegreya-VariableFont_wght.ttf"),
                120 // Font size when first loaded (can be overridden later)
        );

        // Check if the font was successfully loaded (optional for debugging)
        if (customFont != null) {
            System.out.println("Custom font loaded: " + customFont.getName());
        } else {
            System.err.println("Failed to load custom font!");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("firstScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome Screen!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}