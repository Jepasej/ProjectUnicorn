package org.example.musicplayer;

import javafx.scene.media.*;
import java.io.*;
import javafx.util.Duration;
import javafx.scene.media.MediaPlayer;

public class PlayerControls
{
    private MediaPlayer playerCommands;
    private Media track;
    private Duration pausePosition; // Variabel to save pauseposition
    final static Duration SKIP_SECONDS = Duration.seconds(5);
    /**
     * Method to define what song we wish to interact with
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

    public void playTrack()

    {
        if (playerCommands != null) {
            //If "pausebutton" is pressed, continue from where we paused
            if (pausePosition != null) {
                playerCommands.seek(pausePosition); //Resume from pausepositon
            }
            playerCommands.play();
        }
    }



    public void stopTrack()
    {
        playerCommands.stop();
    }

    public void pauseTrack()
    {
        if (playerCommands != null)
        {
            pausePosition = playerCommands.getCurrentTime(); //Save pauseposition
            playerCommands.pause();
        }
    }
    //playerCommands.pause();


    public void seekTrack()
    {
        if (playerCommands != null)
        {
            playerCommands.seek(SKIP_SECONDS);
        }
    }
}
