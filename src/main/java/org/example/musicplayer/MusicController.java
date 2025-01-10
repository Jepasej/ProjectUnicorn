package org.example.musicplayer;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.print.attribute.standard.Media;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MusicController implements Initializable {

    @FXML
    private Label welcomeText;
    @FXML
    private TextArea songTitle;
    @FXML
    private ListView<String> infoSongs;
    @FXML
    private Button buttonPlay, buttonPause, buttonStop, buttonPreviousSong, buttonNextSong, buttonShuffle;
    @FXML
    private MenuButton menuButton;
    @FXML
    private ComboBox searchBox;
    @FXML
    private javafx.scene.image.ImageView pictureFrame;
    @FXML
    private ProgressBar progressBar;


    private Media media;
    private ArrayList<Image> imageList;
    private int songNumber;
    private org.example.musicplayer.ImageDisplay imageDisplay;
    private PlayerControls playerControls;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<String> songs = DisplaySongUI.displaySongInfo();
            infoSongs.setItems(songs);
            imageDisplay = new ImageDisplay();
            displayRandomImage();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCurrentSelection()
    {
        String selection = infoSongs.getSelectionModel().getSelectedItem();
        selection = selection.substring(0,1).trim();
        return Integer.parseInt(selection);
    }
        // Hent sange fra databasen og vis i ListView
        //loadSongsFromDatabase();

    public void displayRandomImage()
    {
        if (imageDisplay != null && !imageDisplay.images.isEmpty()) {
            Image randomImage = imageDisplay.getRandomImage();
            if (randomImage != null) {
                pictureFrame.setImage(randomImage);
                // Print the URI or some other useful info about the image
                System.out.printf("Displayed a random image: %s\n", randomImage.getUrl());
            } else {
                System.out.println("Random image was null.");
            }
        } else {
            System.out.println("Image display is not initialized or contains no images.");
        }
    }

    public void playSong(ActionEvent actionEvent) throws Exception
    {
        int songID = getCurrentSelection();
        DBConnection dbConnection = new DBConnection();
        String filePath = dbConnection.getFilepathFromID(songID);

        playerControls = new PlayerControls();
        playerControls.setTrack(filePath);
        playerControls.playTrack();
    }

    public void pauseSong(ActionEvent actionEvent)
    {
        playerControls.pauseTrack();
    }
}
