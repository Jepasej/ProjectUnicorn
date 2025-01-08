package org.example.musicplayer;

import javafx.scene.media.*;
import java.io.*;
import javafx.util.Duration;

public class PlayerControls
{
    private MediaPlayer playerCommands;
    private Media track;
    final static Duration SKIP_SECONDS = Duration.seconds(5);
    /**
     * Method to define what song we wish to interact with
     * @param songChoicePath the file path of the chosen song, retrieved from the database
     */
    public void setTrack(String songChoicePath)
    {
        String trackPath = new File(songChoicePath).getAbsolutePath();
        track = new Media(new File(trackPath).toURI().toString());

        playerCommands = new MediaPlayer(track);
    }

    public void playTrack()
    {
        playerCommands.play();
    }

    public void stopTrack()
    {
        playerCommands.stop();
    }

    public void pauseTrack()
    {
        playerCommands.pause();
    }

    public void seekTrack()
    {
        playerCommands.seek(SKIP_SECONDS);
    }
}
