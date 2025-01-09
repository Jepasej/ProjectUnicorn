package org.example.musicplayer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class MusicController implements Initializable {

    @FXML
    private ListView<String> songList; // Linket til din ListView fra FXML

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            ObservableList<String> infoSongs = DisplaySongUI.displaySongInfo();
            songList.setItems(infoSongs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Hent sange fra databasen og vis i ListView
        //loadSongsFromDatabase();
    }
}