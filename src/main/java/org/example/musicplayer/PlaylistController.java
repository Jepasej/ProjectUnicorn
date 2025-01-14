package org.example.musicplayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaylistController
{

    @FXML
    private ListView<String> infoSongsInSecondUI,editPlaylistField;
    @FXML
    private Button createPlaylist, editPlaylist, deletePlaylist, addSong, removeSong;

    public void intialize()
    {
        infoSongsInSecondUI.setItems(FXCollections.observableArrayList());
        editPlaylistField.setItems(FXCollections.observableArrayList());
    }

    //Method to switch the entries in the main ListView to the edit field so we later can create playlists.
    private void transferItems(ListView<String> source, ListView<String> destination)
    {
        ObservableList<String> selectedItems = source.getSelectionModel().getSelectedItems();

        if (!selectedItems.isEmpty()) {
            // Add the selected items to the destination ListView
            destination.getItems().addAll(selectedItems);

            // Remove the selected items from the source ListView
            source.getItems().removeAll(selectedItems);
        } else {
            System.out.println("No items selected for transfer.");
        }
    }

    public void transferToEditPlaylist()
    {
        transferItems(infoSongsInSecondUI, editPlaylistField);
    }

    public void transferToInfoSongs()
    {
        transferItems(editPlaylistField, infoSongsInSecondUI);
    }


    // Method to set the items in the ListView
    public void setInfoSongs(ObservableList<String> songs)
    {
        if (infoSongsInSecondUI != null)
        {
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

    public void onCreatePlaylistClick(ActionEvent actionEvent)
    {

    }

    public void onEditPlaylistClick(ActionEvent actionEvent)
    {

    }

    public void onDeletePlaylistClick(ActionEvent actionEvent)
    {

    }
}

//currentPlaylist.addSong(new Song("Song 1", "Artist 1", "path/to/song1"));
//currentPlaylist.addSong(new Song("Song 2", "Artist 2", "path/to/song2"));