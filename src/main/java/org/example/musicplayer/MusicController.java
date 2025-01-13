package org.example.musicplayer;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.print.attribute.standard.Media;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.IOException;
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
    private ListView<String> infoSongs, infoSongsInSecondUI;
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
    private MediaPlayer mediaPlayer;
    private ArrayList<Image> imageList;
    private int songNumber;
    private org.example.musicplayer.ImageDisplay imageDisplay;
    private PlayerControls playerControls;
    public String lastSelectedTrack;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    /**
     * Supplies our UI with the necessary information to display songs and images.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try
        {
            ObservableList<String> songs = DisplaySongUI.displaySongInfo();
            infoSongs.setItems(songs);
            imageDisplay = new ImageDisplay();
            displayRandomImage();
            //System.out.println(infoSongs); //used for bugfixing and bugsearching
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Hent sange fra databasen og vis i ListView
    //loadSongsFromDatabase();

    /**
     * Extracts the songID from our listview
     * @return a songID int which can be used to retrieve a filepath from the DB
     */
    public int getCurrentSelection()
    {
        String selection = infoSongs.getSelectionModel().getSelectedItem();
        selection = selection.substring(0,2).trim();
        return Integer.parseInt(selection);
    }

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


    public void switchToPlaylistScene(javafx.event.ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource("playlistScene.fxml"));
        Parent root = fxmlLoader.load();
        // Retrieve the controller of the second scene
        PlaylistController playlistController = fxmlLoader.getController();
        // Pass the data from infoSongs to infoSongsInSecondUI
        ObservableList<String> songs = infoSongs.getItems(); // Get the items from the first ListView
        playlistController.setInfoSongs(songs);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void playSong(ActionEvent actionEvent) throws Exception
    {
        int songID = getCurrentSelection();
        DBConnection dbConnection = new DBConnection();
        String filePath = dbConnection.getFilepathFromID(songID);

        if(filePath.equals(lastSelectedTrack))
        {
            playerControls.playTrack();
        }
        else
        {
            playerControls = new PlayerControls();
            playerControls.setTrack(filePath);
            playerControls.playTrack();

            if (mediaPlayer != null)
            {
                mediaPlayer.stop();
                mediaPlayer.play();
            }
        }
        lastSelectedTrack = filePath;
    }


    public void pauseSong(ActionEvent actionEvent)
    {
        playerControls.pauseTrack();

    }

    /**
     * Stops the currently playing music track.
     * @param actionEvent
     */
    public void stopSong(ActionEvent actionEvent)
    {
        playerControls.stopTrack();
    }
}
