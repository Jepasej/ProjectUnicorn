package org.example.musicplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.print.attribute.standard.Media;
import javax.swing.text.html.ImageView;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MusicController implements Initializable {

    @FXML
    private Label welcomeText;
    @FXML
    private TextArea songTitle, songView;
    @FXML
    private Button buttonPlay, buttonPause, buttonStop, buttonPreviousSong, buttonNextSong, buttonShuffle;
    @FXML
    private MenuButton menuButton;
    @FXML
    private ComboBox searchBox;
    @FXML
    private ImageView pictureFrame;
    @FXML
    private ProgressBar progressBar;


    private Media media;

    private int songNumber;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    /**
     * This method ....
     * @param url
     * @param resourceBundle
     */

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