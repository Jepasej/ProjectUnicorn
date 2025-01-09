package org.example.musicplayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DisplaySongUI {

    private int songID;
    private String songTitle;
    private int songLength;
    private String artist;
    private String album;

    public static ObservableList<String> displaySongInfo() throws Exception {
        ObservableList<String> songInfo = FXCollections.observableArrayList();
        String query = "SELECT fldSongID, fldName, fldLengthInSeconds, fldArtist, fldAlbum FROM tblSongs";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(query)) {

            while (result.next()) {
                int songID = Integer.parseInt(result.getString("fldSongID"));
                String songTitle = result.getString("fldName");

                songInfo.add(songID + " - " + songTitle);
            }
        }
        return songInfo;
    }
}