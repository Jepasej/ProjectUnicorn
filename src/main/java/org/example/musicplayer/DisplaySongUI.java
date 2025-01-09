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

    public DisplaySongUI(int songID, String songTitle, int songLength, String artist, String album) {
        this.songID = songID;
        this.songTitle = songTitle;
        this.songLength = songLength;
        this.artist = artist;
        this.album = album;
    }

    public DisplaySongUI(){

    }

    public int getSongID() {
        return songID;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public int getSongLength() {
        return songLength;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

}