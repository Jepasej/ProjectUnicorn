package org.example.musicplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.print.attribute.standard.Media;
import javax.swing.text.html.ImageView;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MusicController implements Initializable {

    @FXML
    private Label welcomeText;
    @FXML
    private TextArea songTitle;
    @FXML
    private ListView<String> songList;
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
    private PlayerControls playerControls;

    @FXML
    public void pressPlay()
    {
        playerControls.playTrack();
    }

    @FXML
    public void pressStop()
    {
        playerControls.stopTrack();
    }

    @FXML
    public void pressPause()
    {
        playerControls.pauseTrack();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Hent sange fra databasen og vis i ListView
        //loadSongsFromDatabase();
    }
}