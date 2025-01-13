package org.example.musicplayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;

public class DisplaySongUI {

    /**
     * Initializes an observarable list for the ListView in our UI
     * @return song info of our songs in the DB.
     * @throws Exception If the database og SQL connection operation fails.
     */
    public static ObservableList<String> displaySongInfo() throws Exception {

        /*Connects to our DB and inititlaizes an arraylist with the songs from our method readAllSongsToArray
        from the DBConnection class.
         */
        DBConnection db = new DBConnection();
        ArrayList<Song> songs;
        songs = db.readAllSongsToArray();

        //Declares an empty observableList to hold song info to be used in JavaFX.
        ObservableList<String> songInfo = FXCollections.observableArrayList();

        //Loops through our arraylist songs and retrieves info.
        for (Song song : songs)
        {
                songInfo.add(song.getFldSongID() + " - " + song.getFldName() + " - " + song.getFldArtist() + " - " + song.getFldLengthInSeconds() + "sec");
        }
        return songInfo;
    }
}