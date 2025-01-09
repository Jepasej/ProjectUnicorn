package org.example.musicplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.print.attribute.standard.Media;
import java.net.URL;
import java.util.ArrayList;
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
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        imageDisplay = new ImageDisplay();
        displayRandomImage();
        System.out.printf("Hello world");

    }

/*
    public static void initializeImageDisplay(ImageView display)
    {
        ImageView = display;
    }
*/

    public void displayRandomImage() {
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

}