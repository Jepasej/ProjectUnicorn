package org.example.musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class that launches the Music Player application, loads the scene,
 * and initializes necessary components such as the database connection and music controller.
 */
public class MusicPlayerApplication extends Application
{
    /**
     * The main entry point for launching the application.
     *
     * @param stage The primary stage for this application, onto which the scene is set.
     * @throws Exception If there is an issue with loading the FXML or initializing the application.
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        // Loads the FXML file and sets up the scene
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        // Sets the title of the application window
        stage.setTitle("Your Music");
        // Sets the scene for the stage
        stage.setScene(scene);
        // Displays the stage
        stage.show();

        // Initializes database connection and reads all songs into an array
        DBConnection dbConnection = new DBConnection();
        dbConnection.getConnection();
        dbConnection.readAllSongsToArray();

        // Initializes the music controller
        MusicController mc = new MusicController();

        // Initializes the image display for the music player
        ImageDisplay imageDisplay = new ImageDisplay();

        // Display random image
        mc.displayRandomImage();
    }


    /**
     * The main method that starts the application.
     *
     * @param args Command-line arguments (not used in this case).
     */
    public static void main(String[] args)
    {
        // Launches the JavaFX application
        launch();
    }
}