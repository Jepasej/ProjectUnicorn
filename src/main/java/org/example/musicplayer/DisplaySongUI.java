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

        /*String query = "SELECT fldName, fldLengthInSeconds, fldArtist FROM tblSongs";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(query)) {
            while (result.next()) {
                String songTitle = result.getString("fldName");
                int songLength = Integer.parseInt(result.getString("fldLengthInSeconds"));
                String artist = result.getString("fldArtist");

                songInfo.add(songTitle.trim()+ " - " + artist.trim() + " - " + songLength);

            }
            for (var i : songInfo)
            {
                System.out.println(i);
            }
        }*/

        return songInfo;
    }
}