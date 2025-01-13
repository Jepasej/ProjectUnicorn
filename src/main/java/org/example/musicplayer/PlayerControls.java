package org.example.musicplayer;

import javafx.scene.media.*;
import java.io.*;
import javafx.util.Duration;
import javafx.scene.media.MediaPlayer;

/**
 * Class in charge of the classic media player controls, eg. start/stop
 */
public class PlayerControls
{
    private MediaPlayer playerCommands;
    private Media track;

    /**
     * Method to define what song we wish to interact with.
     * Checks whether the selected song is the same as the song currently playing.
     * @param songChoicePath the file path of the chosen song, retrieved from the database
     */
    public void setTrack(String songChoicePath)
    {
        String trackPath = new File(songChoicePath).getAbsolutePath();
        track = new Media(new File(trackPath).toURI().toString());

        if (playerCommands == null) {
            playerCommands = new MediaPlayer(track);
        } else {
            playerCommands.stop();
            playerCommands.dispose();
            playerCommands = new MediaPlayer(track);
        }
    }

    /**
     * plays selected track
     */
    public void playTrack()
    {
        playerCommands.play();
    }


    /**
     * stops selected track
     */
    public void stopTrack()
    {
        playerCommands.stop();
    }

    /**
     * pauses selected track
     */
    public void pauseTrack()
    {
        if (playerCommands != null)
        {
            playerCommands.pause();
        }
    }
}
