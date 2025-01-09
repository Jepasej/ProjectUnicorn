package org.example.musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class MusicPlayerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("UniPlayer");
        stage.setScene(scene);
        stage.show();

        MusicController mc = new MusicController();
        //mc.initialize();

        ImageDisplay imageDisplay = new ImageDisplay();
        //MusicController.initializeImageDisplay(ImageView);
        mc.displayRandomImage();


    }



    public static void main(String[] args) {
        launch();
        //private char hello = 'c';
    }
}