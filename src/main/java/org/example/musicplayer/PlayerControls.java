package org.example.musicplayer;

import javafx.scene.media.*;
import java.io.*;

public class PlayerControls
{
    private MediaPlayer playerCommands;
    private Media track;

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
}
