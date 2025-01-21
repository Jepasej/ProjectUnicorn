package org.example.musicplayer;

import javafx.collections.FXCollections;
import javafx.application.Platform;
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
import javax.print.attribute.standard.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private ListView<String> infoSongs;
    @FXML
    private TextField searchBox;
    @FXML
    private ComboBox playlistBox;
    @FXML
    private javafx.scene.image.ImageView pictureFrame;
    @FXML
    private ProgressBar progressBar;

    //Controls music playback
    private MediaPlayer mediaPlayer;
    //Handles image display
    private org.example.musicplayer.ImageDisplay imageDisplay;
    //Manges player controls for music playback
    private PlayerControls playerControls;
    //Stores the file path of the last selected track
    public String lastSelectedTrack;
    // Path to the folder containing images for the music player
    private String imageFolderFilepath = null;
    // Timer for  managing time-based actions in the music player
    private Timer timer;
    // A TimerTask that represents the specific task to be executed by the Timer
    private TimerTask task;
    // Observable list to hold all songs in the playlist, used to manage the entire song collection
    private ObservableList<String> allSongs;
    // Observable list to hold the filtered songs, used to show a subset of songs based on user selection
    private ObservableList<String> filteredSongs;



    ObservableList<String> songs = null;
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

        setLabelDuration("");

    }

    /**
     * Plays a startup sound when the application is initialized.
     */
    private void playOnStartup()
    {
        String startupSound = "src/main/resources/org/example/musicplayer/Music/pornhub-intro-made-with-Voicemod.mp3";
        playerControls = new PlayerControls();
        playerControls.setTrack(startupSound);
        playerControls.playTrack();
    }

    /**
     * Initializes the image display object and shows a random image.
     */
    private void populateImageDisplay()
    {
        if(imageFolderFilepath==null)
        {
            //Initializes the image display object
            imageDisplay = new ImageDisplay();

            //Displays a random image in the picture frame
            displayRandomImage();

        } else if (imageFolderFilepath!=null)
        {
            imageDisplay = new ImageDisplay(imageFolderFilepath);
            displayRandomImageFromFolder();
        }
    }

    /**
     * Displays a random image from the list of images in the picture frame.
     */
    private void displayRandomImageFromFolder()
    {
        if (imageDisplay != null && !imageDisplay.images.isEmpty())
        {
            //Retrieves a random image
            Image randomImage = imageDisplay.getRandomImage();
            if (randomImage != null)
            {
                //Sets the random image in the picture frame
                pictureFrame.setImage(randomImage);
            } else {
                System.out.println("Random image was null.");
            }
        } else {
            System.out.println("Image display is not initialized or contains no images.");
        }
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
     * Choose a folder from PC with images to display
     */
    public void setImageFolder()
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        imageFolderFilepath = selectedDirectory.getAbsolutePath();
        populateImageDisplay();
    }

    /**
     * Populates the playlist ComboBox with playlist names from the database.
     */
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

    /**
     * Populates the song list in the UI with songs from the database.
     */
    private void populateInfoSongs()
    {
        try
        {
            //ObservableList<String> songs = DisplaySongUI.displaySongInfo();
            songs = DisplaySongUI.displaySongInfo();
            //Populates the song list in the UI
            infoSongs.setItems(songs);
        } catch (Exception e) {
            //Log any exceptions that occur during initialization
            e.printStackTrace();
        }

        //All songs in our Music player put in to an observable list
        allSongs = FXCollections.observableArrayList(
                "1\tMain Title                 \t\t\tBear McCreary\t\t\t\t65  sec",
                "3\tHelo Chase                  \t\t\tBear McCreary\t\t\t\t91  sec",
                "4\tThe Olympic Carrier         \t\tBear McCreary\t\t\t\t348 sec",
                "5\tHelo Rescued                \t\t\tBear McCreary\t\t\t\t62  sec",
                "7\tA Good Lighter              \t\t\tBear McCreary\t\t\t\t116 sec",
                "9\tThe Thousandth Landing      \t\tBear McCreary\t\t\t\t188 sec",
                "10\tTwo Funerals               \t\t\tBear McCreary\t\t\t\t205 sec",
                "11\tStarbuck Takes On All Eight\t\tBear McCreary\t\t\t\t226 sec",
                "12\tForgiven                   \t\t\tBear McCreary\t\t\t\t90  sec",
                "13\tThe Card Game              \t\t\tBear McCreary\t\t\t\t184 sec",
                "14\tStarbuck On The Red Moon   \t\tBear McCreary\t\t\t\t120 sec",
                "15\tHelo In The Warehouse      \t\tBear McCreary\t\t\t\t121 sec",
                "17\tBaltar Speaks With Adama   \t\tBear McCreary\t\t\t\t114 sec",
                "18\tTwo Boomers                \t\t\tBear McCreary\t\t\t\t107 sec",
                "19\tBattlestar Operatica       \t\t\tBear McCreary\t\t\t\t156 sec",
                "20\tThe Dinner Party           \t\t\tBear McCreary\t\t\t\t194 sec",
                "21\tBattlestar Muzaktica       \t\t\tBear McCreary\t\t\t\t103 sec",
                "22\tBaltar Panics              \t\t\tBear McCreary\t\t\t\t106 sec",
                "23\tBoomer Flees               \t\t\tBear McCreary\t\t\t\t76  sec",
                "24\tFlesh And Bone             \t\t\tBear McCreary\t\t\t\t246 sec");

        //The filtered songs to be shown after user input taken from our allSongs observable list.
        filteredSongs = FXCollections.observableArrayList(allSongs);

        infoSongs.setItems(filteredSongs);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filterListView (newValue);
        });
    }

    /**
     * Filters the list of songs based on the user's search input.
     *
     * @param searchSongs The search term entered by the user.
     */
    private void filterListView(String searchSongs)
    {
        if(searchSongs == null || searchSongs.isEmpty()){
            filteredSongs.setAll(allSongs);
        }else{
            String userInput = searchSongs.toLowerCase();
            List<String> filteredMatches = new ArrayList<>();

            for(String match : allSongs){
                if(match.toLowerCase().contains(userInput)){
                    filteredMatches.add(match);
                }
            }
            filteredSongs.setAll(filteredMatches);
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
            displayRandomImage();

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

    /**
     * Updates the progress bar to reflect the current media playback position.
     * Runs periodically using a TimerTask.
     */
    public void progressBarUI()
    {
        // Get the player commands from the playerControls object
        mediaPlayer = playerControls.getPlayerCommands();
        // Initialize a timer to schedule periodic tasks
        timer = new Timer();
        // Define a TimerTask to update the progress bar at regular intervals
        task = new TimerTask()
        {
            @Override
            public void run() {
                // Run the task on the JavaFX Application Thread
                Platform.runLater(() ->
                {
                // Check if the mediaPlayer is null (if no media is loaded)
                if (mediaPlayer == null) {

                }
                // Proceed if the mediaPlayer and its media are not null
                if (mediaPlayer != null && mediaPlayer.getMedia() != null)
                {
                    // Calculate the current time in seconds and the total duration of the media
                    double currentSeconds = mediaPlayer.getCurrentTime().toSeconds();
                    double end = mediaPlayer.getMedia().getDuration().toSeconds();

                    // Update the progress bar based on the current time and total duration
                    progressBar.setProgress(currentSeconds / end);
                }
            });
            }
        };
        // Schedule the TimerTask to run every 1000 milliseconds (1 second), starting after 100 milliseconds
        timer.scheduleAtFixedRate(task, 100, 1000);
    };


    /**
     * Changes the playlist and updates the song list in the UI.
     *
     * @param actionEvent The event triggered by selecting a new playlist.
     */
    public void changePlaylist(ActionEvent actionEvent)
    {
        try
        {
            // Get the songs associated with the selected playlist and display them in the UI
            ObservableList<String> songs = DisplaySongUI.displayPlaylistSongInfo(playlistBox.getValue().toString());
            //Populates the song list in the UI
            infoSongs.setItems(songs);

        } catch (Exception e) {
            //Log any exceptions that occur during initialization
            e.printStackTrace();
        }
    }
     public void setLabelDuration(String totalDur)
    {

        //totalDurationDisplay.setText(totalDur);
        totalDurationDisplay.setText(DisplaySongUI.totalDur);
     }
}
