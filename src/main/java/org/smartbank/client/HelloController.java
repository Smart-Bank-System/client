package org.smartbank.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private void initialize() {
        Font customFont = Font.loadFont("C:\\Users\\KullaniciX\\IdeaProjects\\client\\src\\main\\resources\\org\\smartbank\\client\\fonts\\Alegreya-VariableFont_wght.ttf", 120);
        welcomeText.setFont(customFont);
    }
}