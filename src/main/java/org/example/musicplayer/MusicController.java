package org.example.musicplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MusicController implements Initializable {

    @FXML
    private Label welcomeText;

    @FXML
    private TextArea songTitle, songView;

    private Media media;

    private int songNumber;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File musicDirectory;
        File[] songFiles;

        ArrayList<File> songs;

        songs = new ArrayList<File>();

        musicDirectory = new File("Music");
        //Gets all the Music numbers within our music directory map
        songFiles = musicDirectory.listFiles();

        if (songFiles != null) {
            for (File file : songFiles) {
                songs.add(file);
                //System.out.println(file);
            }
        }



    }
}