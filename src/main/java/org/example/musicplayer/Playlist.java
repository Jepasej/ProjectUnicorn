package org.example.musicplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent og administrate a playlist
 * Contains methods to add, remove and administrate songs
 */
public class Playlist {
    //Name of playlist
    private String name;

    //List that contains all songs in playlist
    private List<Song> songs;

    /**
     * Contructor to add a new playlist with a chosen name
     *
     * @param name name of playlist
     */

    public Playlist(String name) {
        this.name = name;
        //Initialize list to contain songs
        this.songs = new ArrayList<Song>();
    }

    /**
     * Get name of playlist
     *
     * @return name of playlist
     */
    public String getname() {
        return name;
    }

    /**
     * Set new name for playlist
     *
     * @param name new name of playlist
     */
    public void setname(String name) {
        this.name = name;
    }

    /**
     * Get list of all songs in playlist
     *
     * @return list of song-objects
     */
    public List<Song> getSongs() {
        return songs;
    }

    /**
     * Adds new song to playlist
     *
     * @param song the song that is added
     */
    public void addSong(Song song) {
        songs.add(song);
    }

    /**
     * Removes existing song from playlist
     *
     * @param song the song that is removed
     */

    public void removeSong(Song song) {
        songs.remove(song);
    }

    /**
     * Gets a song from playlist based on its index
     *
     * @param index the index of the song in the List
     * return song on the given index
     */
    public Song getSong(int index) {
        return songs.get(index);
    }

    /**
     * Gets number of songs on playlist
     *
     * @return number of songs
     */
    public int getSize(){
        return songs.size();
    }

    /**
     * Removes all songs from playlist
     */
    public void clearPlaylist() {
        songs.clear();
    }
}
