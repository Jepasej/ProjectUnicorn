package org.example.musicplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MusicController implements Initializable {

    @FXML
    private ListView<String> songList; // Linket til din ListView fra FXML

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Hent sange fra databasen og vis i ListView
        loadSongsFromDatabase();
    }
}