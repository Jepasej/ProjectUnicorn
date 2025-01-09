package org.example.musicplayer;

public class Song
{
    private int fldSongID;
    private String fldName;
    private String fldLengthInSeconds;
    private String fldArtist;
    private String fldAlbum;
    private String fldFilePath;

    public int getFldSongID() {
        return fldSongID;
    }

    public void setFldSongID(int fldSongID) {
        this.fldSongID = fldSongID;
    }

    public String getFldName() {
        return fldName;
    }

    public void setFldName(String fldName) {
        this.fldName = fldName;
    }

    public String getFldLengthInSeconds() {
        return fldLengthInSeconds;
    }

    public void setFldLengthInSeconds(String fldLengthInSeconds) {
        this.fldLengthInSeconds = fldLengthInSeconds;
    }

    public String getFldArtist() {
        return fldArtist;
    }

    public void setFldArtist(String fldArtist) {
        this.fldArtist = fldArtist;
    }

    public String getFldAlbum() {
        return fldAlbum;
    }

    public void setFldAlbum(String fldAlbum) {
        this.fldAlbum = fldAlbum;
    }

    public String getFldFilePath() {
        return fldFilePath;
    }

    public void setFldFilePath(String fldFilePath) {
        this.fldFilePath = fldFilePath;
    }
}
