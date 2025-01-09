package org.example.musicplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javax.print.attribute.standard.Media;
import javax.swing.text.html.ImageView;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
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
    private static ImageView pictureFrame;
    @FXML
    private ProgressBar progressBar;


    private Media media;
    private ArrayList<Image> imageList;
    private int songNumber;
    private static org.example.musicplayer.ImageDisplay ImageDisplay;
    private PlayerControls playerControls;
    private static javax.swing.text.html.ImageView ImageView;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }


    public static void initializeImageDisplay(ImageView display)
    {
        ImageView = display;
    }


    public static void displayRandomImage()
    {
        if (ImageView != null) {
            Image randomImage = ImageDisplay.getRandomImage();
            if (randomImage != null) {
                pictureFrame.getImage();
            }
        }
    }
}