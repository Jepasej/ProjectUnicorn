package org.example.musicplayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;

public class DisplaySongUI {

    public static ObservableList<String> displaySongInfo() throws Exception {

        DBConnection db = new DBConnection();
        ArrayList<Song> songs;
        songs = db.readAllSongsToArray();
        ObservableList<String> songInfo = FXCollections.observableArrayList();

        for (Song song : songs)
        {
                songInfo.add(song.getFldSongID() + " - " + song.getFldName() + " - " + song.getFldArtist() + " - " + song.getFldLengthInSeconds() + "sec");
        }
        return songInfo;
    }
}