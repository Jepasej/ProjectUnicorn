package org.example.musicplayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DisplaySongUI {

    public static ObservableList<String> displaySongInfo() throws Exception {
        ObservableList<String> songInfo = FXCollections.observableArrayList();
        String query = "SELECT fldName, fldLengthInSeconds, fldArtist FROM tblSongs";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(query)) {
            while (result.next()) {
                String songTitle = result.getString("fldName");
                int songLength = Integer.parseInt(result.getString("fldLengthInSeconds"));
                String artist = result.getString("fldArtist");

                songInfo.add(songTitle + " - " + songLength + " - " + artist);

            }
            for (var i : songInfo)
            {
                System.out.println(i);
            }
        }

        return songInfo;
    }
}