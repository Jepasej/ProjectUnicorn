package org.example.musicplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class MusicController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextArea songLengthArea;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void initialize() {


    }
}