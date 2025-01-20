package org.example.musicplayer;

import java.util.ArrayList;
import java.util.List;

//Maybe used later to edit
public class PlaylistManager {
    private List<Integer> songsToAdd = new ArrayList<>();
    private List<Integer> songsToRemove = new ArrayList<>();

    public void queueSongToAdd(int songID) {
        songsToAdd.add(songID);
    }

    public void queueSongToRemove(int songID) {
        songsToRemove.add(songID);
    }

    public List<Integer> getSongsToAdd() {
        return songsToAdd;
    }

    public List<Integer> getSongsToRemove() {
        return songsToRemove;
    }

    public void clearQueues() {
        songsToAdd.clear();
        songsToRemove.clear();
    }
}
