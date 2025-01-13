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
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the music player application, managing UI interactions,
 * song playback, and scene navigation. Handles actions like play, pause,
 * stop, and displaying random images.
 */

public class MusicController implements Initializable
{
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

    //Holds the media file for playback
    private Media media;
    //Controls music playback
    private MediaPlayer mediaPlayer;
    //Stores a list of images for display
    private ArrayList<Image> imageList;
    //Tracks the current song index
    private int songNumber;
    //Handles image display
    private org.example.musicplayer.ImageDisplay imageDisplay;
    //Manges player controls for music playback
    private PlayerControls playerControls;
    //Stores the file path of the last selected track
    public String lastSelectedTrack;
    private Stage stage;
    private Scene scene;
    private Parent root;

    List<String>searchwords = List.of("Bear McCreary", "The ");

    /**
     * Initializes the UI elements and sets up the list of songs and random image display
     *
     * @param url - the location of the FXML file
     * @param resourceBundle - The resource bundle for internationalization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            ObservableList<String> songs = DisplaySongUI.displaySongInfo();
            //Populates the song list in the UI
            infoSongs.setItems(songs);
            //Initializes the image display object
            imageDisplay = new ImageDisplay();
            //Displays a random image in the picture frame
            displayRandomImage();
        } catch (Exception e) {
            //Log any exceptions that occur during initialization
            e.printStackTrace();
        }
    }

    /**
     * Extracts the songID from our listview
     * @return a songID int which can be used to retrieve a filepath from the DB
     */
    public int getCurrentSelection()
    {
        String selection = infoSongs.getSelectionModel().getSelectedItem();
        //Extracts the first two characters and trims whitespace
        selection = selection.substring(0,2).trim();
        //Converts the extracted ID to an integer
        return Integer.parseInt(selection);
    }

    /**
     * Displays a random image from the list of images in the picture frame
     */
    public void displayRandomImage()
    {
        if (imageDisplay != null && !imageDisplay.images.isEmpty())
        {
            //Retrieves a random image
            Image randomImage = imageDisplay.getRandomImage();
            if (randomImage != null)
            {
                //Sets the random image in the picture frame
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

    /**
     * Switches to the playlist scene
     *
     * @param event The ActionEvent triggered by the user interaction
     * @throws Exception If the scene switch fails
     */
    public void switchToPlaylistScene(javafx.event.ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource("playlistScene.fxml"));
        Parent root = fxmlLoader.load();
        //Retrieves the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        //Sets the new scene
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Plays the selected song from the Listview. If the selected song is the same as the last one, resumes playback.
     *
     * @param actionEvent The ActionEvent triggered by the play button
     * @throws Exception If an error occurs while retrieving or playing the song.
     */
    public void playSong(ActionEvent actionEvent) throws Exception
    {
        int songID = getCurrentSelection();
        DBConnection dbConnection = new DBConnection();
        //Retrieves the file path from the database
        String filePath = dbConnection.getFilepathFromID(songID);

        if(filePath.equals(lastSelectedTrack))
        {
            //Resumes playback of the current track
            playerControls.playTrack();
        }
        else
        {
            playerControls = new PlayerControls();
            //Sets the new track
            playerControls.setTrack(filePath);
            playerControls.playTrack();

            if (mediaPlayer != null)
            {
                //Stops the previous track
                mediaPlayer.stop();
                //Starts playing the new track
                mediaPlayer.play();
            }
        }
        //Updates the last selected track
        lastSelectedTrack = filePath;
    }

    /**
     * Switches to the main UI scene
     *
     * @param event The ActionEvent triggered by the scene switch.
     * @throws IOException If the FXML file cannot be loaded.
     */
    public void switchToFrontUI(javafx.event.ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource("hello-view.fxml"));
        // Ensure you load the FXML
        Parent root = fxmlLoader.load();
        // Retrieves the current stage
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        //Sets the new scene
        stage.setScene(scene);
        stage.show();
    }

    /** Pauses the currently playing song
     *
     * @param actionEvent The ActionEvent triggered by the pause button.
     */
    public void pauseSong(ActionEvent actionEvent)
    {
        //Pauses the current track
        playerControls.pauseTrack();
    }

    /**
     * Stops the currently playing song.
     * @param actionEvent The ActionEvent triggered by the stop button.
     */
    public void stopSong(ActionEvent actionEvent)
    {
        //Stops the current track
        playerControls.stopTrack();
    }
}
