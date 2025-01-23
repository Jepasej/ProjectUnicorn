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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


public class PlaylistController
{
    @FXML
    private ListView<String> infoSongsInSecondUI,editPlaylistField, playlistListview;
    @FXML
    private TextField playlistName;

    private ObservableList<String> editWindow = FXCollections.observableArrayList();


    /**
     * Initializing the Second UI, preparing the arrays and filling the right side with our playlists.
     */
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


    /**
     * Method to switch the entries in the main ListView to the edit field so we later can create playlists.
     * @param source
     * @param destination
     */
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


    /**
     * Methods to transfer what is displayed from one list view to the other
     */

    public void transferToEditPlaylist()
    {
        transferItems(infoSongsInSecondUI, editPlaylistField);
    }

    public void transferToInfoSongs()
    {
        transferItems(editPlaylistField, infoSongsInSecondUI);
    }


    /**
     *  Method to set the items in the ListView
     * @param songs
     */
    public void setInfoSongs(ObservableList<String> songs)
    {
        if (infoSongsInSecondUI != null)
        {
            infoSongsInSecondUI.setItems(songs);
        } else {
            System.out.println("infoSongsInSecondUI is null!");
        }
    }


    /**
     *  On action click, switching to our front UI again, loading the FXML file that controlls the frontpage
     * @param event
     * @throws IOException
     */
    public void switchToFrontUI(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load(); // Ensure you load the FXML
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Fixed parentheses
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Handles the event triggered by clicking the 'Save Playlist' button.
     * This method creates a new playlist with the specified name, extracts song IDs
     * from the selected items in the ListView, and saves the playlist to the database.
     * Finally, it refreshes the ListView to reflect the changes.
     * @param actionEvent
     * @throws Exception
     */
    public void onSavePlaylistClick(ActionEvent actionEvent) throws Exception {
        Playlist newPlaylist = new Playlist(playlistName.getText());
        // List to store the IDs of the songs to be added to the playlist
        ArrayList<Integer> songsToAdd = new ArrayList<>();
        List<String> currentSelection = editPlaylistField.getItems();


        for(int i = 0; i < currentSelection.size(); i++)
        {
            String selection = currentSelection.get(i);
            // Extract the first two characters of the string, which represent the song ID, and trim whitespace
            selection = selection.substring(0,2).trim();

            songsToAdd.add(Integer.parseInt(selection));
        }

        newPlaylist.setSongIDs(songsToAdd);

        DBConnection dbConnection = new DBConnection();
        dbConnection.createPlaylist(newPlaylist);

        //Refresh the ListView
        initialize();
    }


    /**
     * Method to edit our playlists, deletes the chosen songs that were removed from the ListView "EditField" and at the same time adds the songs added to the ListView.
     * Using Batch: Instead of executing a single query, we can execute a batch (group) of queries. It makes the performance fast. It is because when one sends multiple statements of SQL at once to the database, the communication overhead is reduced significantly, as one is not communicating with the database frequently, which in turn results to fast performance.
     * @param actionEvent
     * @throws Exception
     */
    public void onEditPlaylistClick(ActionEvent actionEvent) throws Exception {
        // Create a new Playlist object using the name entered by the user
       Playlist newPlaylist = new Playlist(playlistName.getText());
        // Establish a connection to the database
       DBConnection dbConnection = new DBConnection();
       try{
           // Attempt to retrieve the playlist ID from the database using the new Playlist object
           int playlistID = dbConnection.getPlaylistID(newPlaylist);
           System.out.println("Playlist ID: " + playlistID);
       }catch (Exception e)
       {
           // Handle any exception that occurs while retrieving the playlist ID
           e.printStackTrace();
       }
        // Attempt to retrieve the playlist ID from the database
        int playlistID = dbConnection.getPlaylistID(newPlaylist);

        // Define SQL queries for deleting existing songs and inserting new songs into the playlist
        String deleteSQL = "DELETE FROM tblSongsPlaylists WHERE fldPlaylistID = ?";
        String insertSQL = "INSERT INTO tblSongsPlaylists (fldPlaylistID, fldSongID, fldOrderNo) VALUES (?, ?, ?)";

        // Create a list to store the song IDs that the user wants to add to the playlist
        ArrayList<Integer> songsToAdd = new ArrayList<>();
        List<String> currentSelection = editPlaylistField.getItems();

        // Loop through each selected song, extract the song ID, and add it to the songsToAdd list
        for (String selection : currentSelection) {
            try {
                // Extract the first two characters of the selection string, trim whitespace, and convert to an integer
                int songID = Integer.parseInt(selection.substring(0, 2).trim());
                songsToAdd.add(songID);
            } catch (NumberFormatException e) {
                // If the song ID format is invalid (e.g., not an integer), print an error message
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
     * Deletes the selected playlist from the database and updates the UI.
     * If no playlist is selected, the method exits with a message.
     * @param actionEvent the event triggered by the "Delete" button
     *Deleting the chosen playlist from the Database,
     * @param actionEvent
     */

    public void onDeletePlaylistClick(ActionEvent actionEvent)
    {
        //Get selected playlist from the Listview
        String selectedPlaylist = playlistListview.getSelectionModel().getSelectedItem();

        //Check if a playlist has been selected
        if (selectedPlaylist == null)
        {
            //Display that no playlist was chosen
            System.out.println("No playlist chosen.");
            //Exit the method as there is nothing to delete
            return;
        }

        try
        {
            //Initialize the database connection
            DBConnection dbConnection = new DBConnection();
            // Attempt to delete the selected playlist from the database
            dbConnection.deletePlaylist(selectedPlaylist);

            // Remove the deleted playlist from the ListView
            playlistListview.getItems().remove(selectedPlaylist);

            // Clear any songs in the edit field
            editPlaylistField.getItems().clear();

            System.out.println("Playlist has been deleted.");
        } catch (Exception e) {
            //Print the exception stack trace for debugging
            e.printStackTrace();
            System.out.println("Could not delete playlist.");
        }
    }


    /**
     * Sets the editplaylist ListView to the playlist chosen by clicking with the mouse.
     * @param mouseEvent
     */
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