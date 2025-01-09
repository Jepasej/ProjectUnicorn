package org.example.musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MusicPlayerApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        DBConnection dbConnection = new DBConnection();
        dbConnection.getConnection();
        dbConnection.readSongsForStart();
    }

    public static void main(String[] args) {
        launch();
        //private char hello = 'c';
    }
}