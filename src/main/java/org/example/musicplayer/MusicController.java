package org.example.musicplayer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import java.util.TimerTask;
import java.util.Timer;

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
    private ComboBox playlistBox;
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
    //Current playlist object
    private Playlist currentPlaylist;
    //Handles image display
    private org.example.musicplayer.ImageDisplay imageDisplay;
    //Manges player controls for music playback
    private PlayerControls playerControls;
    //Stores the file path of the last selected track
    public String lastSelectedTrack;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Timer timer;
    private TimerTask task;

    /**
     * Initializes the UI elements and sets up the list of songs and random image display
     *
     * @param url - the location of the FXML file
     * @param resourceBundle - The resource bundle for internationalization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        playOnStartup();

        populateInfoSongs();

        populateImageDisplay();

        populatePlaylistBox();

    }

    private void playOnStartup()
    {
        String startupSound = "src/main/resources/org/example/musicplayer/Music/pornhub-intro-made-with-Voicemod.mp3";
        playerControls = new PlayerControls();
        playerControls.setTrack(startupSound);
        playerControls.playTrack();
    }

    private void populateImageDisplay()
    {
        //Initializes the image display object
        imageDisplay = new ImageDisplay();
        //Displays a random image in the picture frame
        displayRandomImage();
    }

    private void populatePlaylistBox()
    {
        //Load playlists from the database and add them to the ListView
        try
        {
            DBConnection dbConnection = new DBConnection();
            ArrayList <Playlist> playlists = dbConnection.readAllPlaylists();
            ObservableList<String> playlistNames = FXCollections.observableArrayList();

            for (Playlist playlist : playlists)
            {
                playlistNames.add(playlist.getname());
            }
            playlistBox.setItems(playlistNames);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load playlists from DB");
        }
    }

    private void populateInfoSongs()
    {
        try
        {
            ObservableList<String> songs = DisplaySongUI.displaySongInfo();
            //Populates the song list in the UI
            infoSongs.setItems(songs);


            //System.out.println(infoSongs); //used for bugfixing and bugsearching
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
        // Retrieve the controller of the second scene
        PlaylistController playlistController = fxmlLoader.getController();
        //Create playlist (and add songs)
        Playlist currentPlaylist = new Playlist("My Playlist");

        // Pass the data from infoSongs to infoSongsInSecondUI
        ObservableList<String> songs = infoSongs.getItems(); // Get the items from the first ListView
        playlistController.setInfoSongs(songs);

        //Send songs to PlaylistController
        playlistController.setInfoSongs(songs);

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
        progressBarUI();
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

    public void progressBarUI()
    {
        //System.out.printf("Testing");
        mediaPlayer = playerControls.getPlayerCommands();
        timer = new Timer();
        task = new TimerTask()
        {
            @Override
            public void run() {
                //System.out.printf("TestingRun");
                Platform.runLater(() ->
                {
                //System.out.printf("TestingRunLater");
                if (mediaPlayer == null) {
                    //System.out.println("MediaPlayer is null");
                }
                if (mediaPlayer != null && mediaPlayer.getMedia() != null)
                {
                    //System.out.println("Playing " + mediaPlayer.getMedia());
                    double currentSeconds = mediaPlayer.getCurrentTime().toSeconds();
                    double end = mediaPlayer.getMedia().getDuration().toSeconds();
                    progressBar.setProgress(currentSeconds / end);
                    //System.out.println(currentSeconds / end);
                }
            });
            }
        };
        timer.scheduleAtFixedRate(task, 100, 1000);
    };

    public void changePlaylist(ActionEvent actionEvent)
    {
        try
        {
            ObservableList<String> songs = DisplaySongUI.displayPlaylistSongInfo(playlistBox.getValue().toString());
            //Populates the song list in the UI
            infoSongs.setItems(songs);

            //System.out.println(infoSongs); //used for bugfixing and bugsearching
        } catch (Exception e) {
            //Log any exceptions that occur during initialization
            e.printStackTrace();
        }
    }

    public void goToImageFolderView(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource("playlistScene.fxml"));
        Parent root = fxmlLoader.load();
        // Retrieve the controller of the second scene
        PlaylistController playlistController = fxmlLoader.getController();
        //Create playlist (and add songs)
        Playlist currentPlaylist = new Playlist("My Playlist");

        // Pass the data from infoSongs to infoSongsInSecondUI
        ObservableList<String> songs = infoSongs.getItems(); // Get the items from the first ListView
        playlistController.setInfoSongs(songs);

        //Send songs to PlaylistController
        playlistController.setInfoSongs(songs);

        //Retrieves the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        //Sets the new scene
        stage.setScene(scene);
        stage.show();
    }
}
