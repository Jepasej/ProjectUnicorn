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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static org.example.musicplayer.DBConnection.getConnection;


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

    public void switchToFrontUI(ActionEvent event) throws IOException
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

    public void onEditPlaylistClick(ActionEvent actionEvent) throws Exception {
       Playlist newPlaylist = new Playlist(playlistName.getText());
       DBConnection dbConnection = new DBConnection();
       try{
           int playlistID = dbConnection.getPlaylistID(newPlaylist);
           System.out.println("Playlist ID: " + playlistID);
       }catch (Exception e)
       {
           e.printStackTrace();
       }

        int playlistID = dbConnection.getPlaylistID(newPlaylist);

        String deleteSQL = "DELETE FROM tblSongsPlaylists WHERE fldPlaylistID = ?";
        String insertSQL = "INSERT INTO tblSongsPlaylists (fldPlaylistID, fldSongID, fldOrderNo) VALUES (?, ?, ?)";

        ArrayList<Integer> songsToAdd = new ArrayList<>();
        List<String> currentSelection = editPlaylistField.getItems();

        for (String selection : currentSelection) {
            try {
                int songID = Integer.parseInt(selection.substring(0, 2).trim());
                songsToAdd.add(songID);
            } catch (NumberFormatException e) {
                System.err.println("Invalid song ID format: " + selection);
            }
        }
        newPlaylist.setSongIDs(songsToAdd);

        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            // Delete existing songs from the playlist
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {
                deleteStmt.setInt(1, playlistID);
                deleteStmt.executeUpdate();
            }

            // Insert new songs into the playlist
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
                for (int i = 1; i <= songsToAdd.size(); i++) {
                    insertStmt.setInt(1, playlistID);
                    insertStmt.setInt(2, songsToAdd.get((i-1)));
                    insertStmt.setInt(3, i);
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }

            connection.commit(); // Commit transaction
        } catch (Exception e) {
            e.printStackTrace();
            // Rollback transaction in case of error
            try (Connection connection = dbConnection.getConnection()) {
                connection.rollback();
            }
        }

    }

    /**
     *
     * @param actionEvent
     */

    public void onDeletePlaylistClick(ActionEvent actionEvent)
    {
        String selectedPlaylist = playlistListview.getSelectionModel().getSelectedItem();

        if (selectedPlaylist == null)
        {
            System.out.println("No playlist chosen.");
            return;
        }

        try
        {
            // Call DBConnection to delete the playlist
            DBConnection dbConnection = new DBConnection();
            dbConnection.deletePlaylist(selectedPlaylist);

            // Remove the playlist from the ListView
            playlistListview.getItems().remove(selectedPlaylist);

            // Clear any songs in the edit field
            editPlaylistField.getItems().clear();

            System.out.println("Playlist has been deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not delete playlist.");
        }
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