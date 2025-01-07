package org.example.musicplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MusicController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}