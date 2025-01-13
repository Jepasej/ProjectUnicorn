package org.example.musicplayer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaylistController
{

    @FXML
    private ListView<String> infoSongsInSecondUI;

    // Method to set the items in the ListView
    public void setInfoSongs(ObservableList<String> songs) {
        if (infoSongsInSecondUI != null) {
            infoSongsInSecondUI.setItems(songs);
        } else {
            System.out.println("infoSongsInSecondUI is null!");
        }
    }

    public void switchToFrontUI(javafx.event.ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load(); // Ensure you load the FXML
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Fixed parentheses
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void createPlaylist(javafx.event.ActionEvent event) throws IOException
    {

    }
}

//currentPlaylist.addSong(new Song("Song 1", "Artist 1", "path/to/song1"));
//currentPlaylist.addSong(new Song("Song 2", "Artist 2", "path/to/song2"));