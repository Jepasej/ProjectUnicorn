package org.example.musicplayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;

public class DisplaySongUI {

    public static String totalDur = "";
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
        System.out.println("songs: " + songs.size());
        int totalDuration = 0;

        //Declares an empty observableList to hold song info to be used in JavaFX.
        ObservableList<String> songInfo = FXCollections.observableArrayList();

        //Loops through our arraylist songs and retrieves info.
        for (Song song : songs)
        {
                songInfo.add(song.getFldSongID() + " - " + song.getFldName() + " - " + song.getFldArtist() + " - " + song.getFldLengthInSeconds() + "sec");
                totalDuration += song.getFldLengthInSeconds();
        }
        int minutes = totalDuration / 60;
        int seconds = totalDuration % 60;

        System.out.println("Total Duration: " + minutes + ":0" + seconds);

        if(seconds < 10)
        {
            String totalDur = "Total Duration: " + minutes + ":0" + seconds;
            System.out.println(totalDur);
            //MusicController mc = new MusicController();
            //mc.setLabelDuration(totalDur);
        }
        else
        {
            String totalDurL = "Total Duration: " + minutes + ":" + seconds;
            //MusicController mc = new MusicController();
            totalDur = totalDurL;
            //mc.setLabelDuration(totalDur);
        }

        return songInfo;
    }

    public static ObservableList<String> displayPlaylistSongInfo(String playlistName) throws Exception
    {
        /*Connects to our DB and inititlaizes an arraylist with the songs from our method readSomeSongsToArray
        from the DBConnection class.*/
        DBConnection db = new DBConnection();
        ArrayList<Song> songs;
        songs = db.readSomeSongsToArray(playlistName);
        int totalDuration = 0;

        //Declares an empty observableList to hold song info to be used in JavaFX.
        ObservableList<String> songInfo = FXCollections.observableArrayList();

        //Loops through our arraylist songs and retrieves info.
        for (Song song : songs)
        {
            songInfo.add(song.getFldSongID() + " - " + song.getFldName() + " - " + song.getFldArtist() + " - " + song.getFldLengthInSeconds() + "sec");
            totalDuration += song.getFldLengthInSeconds();
        }
        int minutes = totalDuration / 60;
        int seconds = totalDuration % 60;

        if(seconds < 10)
        {
            songInfo.add("Total Duration: " + minutes + ":0" + seconds);
        }
        else
        {
            songInfo.add("Total Duration: " + minutes + ":" + seconds);
        }

        return songInfo;
    }
}