package org.example.musicplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent og administrate a playlist
 * Contains methods to add, remove and administrate songs
 */
public class Playlist
{
    //Name of playlist
    private String name;

    //ID of playlist
    private int fldPlaylistID;

    //List that contains all songs in playlist
    private List<Song> songs;

    //ArrayList of songIDs
    private ArrayList<Integer> songIDs;

    /**
     * Getter for ArrayList songIDs
     * @return returns an ArrayList of songIDs
     */

    public ArrayList<Integer> getSongIDs()
    {
        return songIDs;
    }

    /**
     * Setter for ArrayList songIDs
     * @param songIDs songIDs to be set as this.songIDs
     */

    public void setSongIDs(ArrayList<Integer> songIDs)
    {
        this.songIDs = songIDs;
    }

    /**
     * Contructor to add a new playlist with a chosen name
     *
     * @param name name of playlist
     */

    public Playlist(String name)
    {
        this.name = name;
        //Initialize list to contain songs
        this.songs = new ArrayList<Song>();
    }

    /**
     * Get name of playlist
     *
     * @return name of playlist
     */
    public String getname()
    {
        return name;
    }

    /**
     * Set new name for playlist - was meant to be added as part of playlist editing
     *
     * @param name new name of playlist
     */
    public void setname(String name)
    {
        this.name = name;
    }

    /**
     * Get list of all songs in playlist - was made for further playlist functionality, not yet implemented
     *
     * @return list of song-objects
     */
    public List<Song> getSongs()
    {
        return songs;
    }

    /**
     * Adds new song to playlist - was made for further playlist functionality, not yet implemented
     *
     * @param song the song that is added
     */
    public void addSong(Song song)
    {
        songs.add(song);
    }

    /**
     * Removes existing song from playlist - was made for further playlist functionality, not yet implemented
     *
     * @param song the song that is removed
     */

    public void removeSong(Song song)
    {
        songs.remove(song);
    }

    /**
     * Gets a song from playlist based on its index - was made for further playlist functionality, not yet implemented
     *
     * @param index the index of the song in the List
     * return song on the given index
     */
    public Song getSong(int index)
    {
        return songs.get(index);
    }

    /**
     * Gets number of songs on playlist - was made for further playlist functionality, not yet implemented
     *
     * @return number of songs
     */
    public int getSize(){
        return songs.size();
    }

    /**
     * Removes all songs from playlist - was made for further playlist functionality, not yet implemented
     */
    public void clearPlaylist()
    {
        songs.clear();
    }

    /**
     * Gets playlistID
     * @return playlistID
     */
    public int getFldPlaylistID()
    {
        return fldPlaylistID;
    }

    /**
     * Sets new ID for Playlist
     * @param fldPlaylistID
     */
    public void setFldPlaylistID(int fldPlaylistID)
    {
        this.fldPlaylistID = fldPlaylistID;
    }
}
