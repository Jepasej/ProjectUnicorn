package org.example.musicplayer;

/**
 * Class representing songs in our music player
 */
public class Song
{
    private int fldSongID;
    private String fldName;
    private int fldLengthInSeconds;
    private String fldArtist;
    private String fldAlbum;
    private String fldFilePath;


    /**
     * retrieves song id from song object
     * @return SongID of song
     */
    public int getFldSongID() {
        return fldSongID;
    }


    /**
     * sets SongID on Song object
     * @param fldSongID desired SongID
     */
    public void setFldSongID(int fldSongID) {
        this.fldSongID = fldSongID;
    }


    /**
     * retrieves song name from song object
     * @return name of song
     */
    public String getFldName() {
        return fldName;
    }


    /**
     * sets name on Song object
     * @param fldName desired Song name
     */
    public void setFldName(String fldName) {
        this.fldName = fldName;
    }


    /**
     * retrieves length of song object (in seconds)
     * @return length of song
     */
    public int getFldLengthInSeconds() {
        return fldLengthInSeconds;
    }


    /**
     * sets length (in seconds) on Song object
     * @param fldLengthInSeconds desired length
     */
    public void setFldLengthInSeconds(int fldLengthInSeconds) {
        this.fldLengthInSeconds = fldLengthInSeconds;
    }


    /**
     * retrieves artist name from Song object
     * @return artist name
     */
    public String getFldArtist() {
        return fldArtist;
    }


    /**
     * sets artist name on Song object
     * @param fldArtist desired artist name
     */
    public void setFldArtist(String fldArtist) {
        this.fldArtist = fldArtist;
    }


    /**
     * retrieves album name from Song object
     * @return album name
     */
    public String getFldAlbum() {
        return fldAlbum;
    }


    /**
     * sets album name on Song object
     * @param fldAlbum desired album name
     */
    public void setFldAlbum(String fldAlbum) {
        this.fldAlbum = fldAlbum;
    }


    /**
     * retrieves filepath from Song object
     * @return filepath of song
     */
    public String getFldFilePath() {
        return fldFilePath;
    }


    /**
     * sets filepath on Song object
     * @param fldFilePath desired filepath
     */
    public void setFldFilePath(String fldFilePath) {
        this.fldFilePath = fldFilePath;
    }
}
