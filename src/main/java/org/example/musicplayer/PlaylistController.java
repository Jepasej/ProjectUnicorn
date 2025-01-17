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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistController
{

    @FXML
    private ListView<String> infoSongsInSecondUI,editPlaylistField, playlistListview;
    @FXML
    private Button savePlaylist, editPlaylist, deletePlaylist, addSong, removeSong;
    @FXML
    private TextField playlistName;

    private ObservableList<String> editWindow = FXCollections.observableArrayList();

    public void initialize()
    {
        //Initialize the ListView with empty observable lists
        infoSongsInSecondUI.setItems(FXCollections.observableArrayList());
        editPlaylistField.setItems(FXCollections.observableArrayList());

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
            playlistListview.setItems(playlistNames);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load playlists from DB");
        }
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

    public void onSavePlaylistClick(ActionEvent actionEvent) throws Exception {
        Playlist newPlaylist = new Playlist(playlistName.getText());
        ArrayList<Integer> songsToAdd = new ArrayList<>();
        List<String> currentSelection = editPlaylistField.getItems();


        for(int i = 0; i < currentSelection.size(); i++)
        {
            String selection = currentSelection.get(i);
            selection = selection.substring(0,2).trim();

            songsToAdd.add(Integer.parseInt(selection));
        }

        newPlaylist.setSongIDs(songsToAdd);

        DBConnection dbConnection = new DBConnection();
        dbConnection.createPlaylist(newPlaylist);

        //Refresh the ListView
        initialize();
    }

    public void onEditPlaylistClick(ActionEvent actionEvent)
    {

    }

    public void onDeletePlaylistClick(ActionEvent actionEvent)
    {

    }

    public void displayPlaylist(MouseEvent mouseEvent)
    {
        try
        {
            ObservableList<String> songs = DisplaySongUI.displayPlaylistSongInfo(playlistListview.getSelectionModel().getSelectedItem());
            //Populates the song list in the UI
            editPlaylistField.setItems(songs);
            playlistName.setText(playlistListview.getSelectionModel().getSelectedItem());

            //System.out.println(infoSongs); //used for bugfixing and bugsearching
        } catch (Exception e) {
            //Log any exceptions that occur during initialization
            e.printStackTrace();
        }
    }
}

//currentPlaylist.addSong(new Song("Song 1", "Artist 1", "path/to/song1"));
//currentPlaylist.addSong(new Song("Song 2", "Artist 2", "path/to/song2"));