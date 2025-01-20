package org.example.musicplayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 * Manages database connections and operations for the music player
 */
public class DBConnection
{
    //Initializes variables for the getConnection method.
    static final String URL = "jdbc:sqlserver://localhost;portNumber=1433;databaseName=DbMusicPlayer;TrustServerCertificate=true;";
    static final String USERNAME = "sa";
    static final String PASSWORD = "admin";

    /**
     * Establishes a connection to the database.
     *
     * @return a Connection object
     * @throws Exception if the connection fails
     */
    public static Connection getConnection() throws Exception
    {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conn;
}

    /**
     * Initializes an arraylist with data from tblSongs from our database
     * @return a list of all songs
     * @throws Exception If the database or SQL connection operation fails
     */
    public ArrayList readAllSongsToArray() throws Exception
    {
        //Declares an arraylist and connects to the DB.
        ArrayList<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM tblSongs";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        //Loops through every song until none left and retrieves all information about the songs from out DB.
        while (rs.next())
        {
            Song song = new Song();
            song.setFldSongID(rs.getInt(1));
            song.setFldName(rs.getString(2).trim());
            song.setFldLengthInSeconds(rs.getInt(3));
            song.setFldArtist(rs.getString(4).trim());
            song.setFldAlbum(rs.getString(5).trim());
            song.setFldFilePath(rs.getString(6).trim());
            songs.add(song);
        }
        return songs;
    }

    /**
     * Retrieves songs associated with a specific playlist.
     *
     * @param playlistName the name of the playlist
     * @return a list of songs in the playlist
     * @throws Exception if a database error occurs
     */
    public ArrayList<Song> readSomeSongsToArray(String playlistName) throws Exception
    {
        Playlist desiredPlaylist = new Playlist(playlistName);
        desiredPlaylist.setFldPlaylistID(getPlaylistID(desiredPlaylist));

        //Declares an arraylist and connects to the DB.
        ArrayList<Song> songs = new ArrayList<>();
        String sql = "SELECT tblSongs.* FROM tblSongs INNER JOIN tblSongsPlaylists ON tblSongs.fldSongID = tblSongsPlaylists.fldSongID INNER JOIN tblPlaylists ON tblPlaylists.fldPlaylistID = tblSongsPlaylists.fldPlaylistID WHERE tblPlaylists.fldPlaylistID = ?";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,desiredPlaylist.getFldPlaylistID());
        ResultSet rs = pstmt.executeQuery();

        //Loops through every song until none left and retrieves all information about the songs from out DB.
        while (rs.next())
        {
            Song song = new Song();
            song.setFldSongID(rs.getInt(1));
            song.setFldName(rs.getString(2).trim());
            song.setFldLengthInSeconds(rs.getInt(3));
            song.setFldArtist(rs.getString(4).trim());
            song.setFldAlbum(rs.getString(5).trim());
            song.setFldFilePath(rs.getString(6).trim());
            songs.add(song);
        }
        return songs;
    }

    /**
     * Takes a playlist object and inserts it into the database
     * @param newPlaylist playlist object created with UI
     * @throws Exception
     */
    public void createPlaylist(Playlist newPlaylist) throws Exception
    {
        String sql = "INSERT INTO tblPlaylists (fldName) VALUES (?)";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newPlaylist.getname());
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0)
        {
            System.out.println("Playlist added successfully.");
        } else {
            System.out.println("Failed to add the Playlist.");
        }

        newPlaylist.setFldPlaylistID(getPlaylistID(newPlaylist));

        createBridgeLink(newPlaylist);
    }

    /**
     * Retrieves the ID of a playlist from the database based on its name.
     *
     * @param newPlaylist The playlist object containing the name of the playlist to be searched.
     * @return The ID of the playlist.
     * @throws Exception If there is an issue with the database connection or SQL query execution.
     */
    private int getPlaylistID(Playlist newPlaylist) throws Exception
    {
        int playlistID = 0;

        // // SQL query to retrieve the playlist ID based on the playlist name.
        String sql = "SELECT fldPlaylistID FROM tblPlaylists WHERE fldName = ?";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,newPlaylist.getname());
        ResultSet rs = pstmt.executeQuery();

        boolean hasPlaylistID = false;

        // Loops through the result set and retrieves the playlist ID.
        while (rs.next())
        {
            hasPlaylistID = true;
            // Retrieves the playlist ID from the result set.
            playlistID = rs.getInt(1);
        }

        // If no playlist ID is found, print a message indicating no match was found.
        if (!hasPlaylistID)
        {
            System.out.println("No songs found.");
        }

        // Returns the playlist ID (or 0 if not found)
        return playlistID;
    }

    /**
     * Updates the bridgetable to connect a playlist to specific songs.
     * @param newPlaylist playlist received from cratePlaylist()
     * @throws Exception
     */
    private void createBridgeLink(Playlist newPlaylist) throws Exception
    {
        ArrayList<Integer> songsToBridgeTable = newPlaylist.getSongIDs();

        for(int i = 1; i <= songsToBridgeTable.size(); i++)
        {
            String sql = "INSERT INTO tblSongsPlaylists (fldSongID, fldPlaylistID, fldOrderNo) VALUES (?, ?, ?)";
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, songsToBridgeTable.get(i-1));
            pstmt.setInt(2, newPlaylist.getFldPlaylistID());
            pstmt.setInt(3, i);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Bridgelink added successfully.");
            } else {
                System.out.println("Failed to add the Bridgelink.");
            }
        }
    }

    /**
     * Retrieves the filepath from our database for a specific chosen song in our song arrayList.
     * @param id The songID from our database
     * @return returns the filepath for the chosen song
     * @throws Exception If the database og SQL connection operation fails.
     */
    public String getFilepathFromID(int id) throws Exception
    {
        //Connects to our DB and retrieves all information for a specific chosen song from our DB.
        String sql = "SELECT * FROM tblSongs WHERE FldSongID = ?";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        boolean hasSongs = false;

        //Loops through our tblSong in our DB until match with songID and retrieves all info on that song.
        while (rs.next())
        {
            hasSongs = true;
            Song song = new Song();
            song.setFldSongID(rs.getInt(1));
            song.setFldName(rs.getString(2).trim());
            song.setFldLengthInSeconds(rs.getInt(3));
            song.setFldArtist(rs.getString(4).trim());
            song.setFldAlbum(rs.getString(5).trim());
            song.setFldFilePath(rs.getString(6).trim());

            return song.getFldFilePath();
        }
        if (!hasSongs) {
            System.out.println("No songs found.");
        }
    return null;
    }

    /**
     * Retrieves all playlists from the database.
     *
     * @return A list of Playlist objects containing all the playlists from the database.
     * @throws Exception If there is an issue with the database connection or SQL query execution.
     */
    public ArrayList<Playlist> readAllPlaylists() throws Exception
    {
        ArrayList<Playlist> playlists = new ArrayList<>();
        // SQL query to select all playlist IDs and names from the database.
        String sql = "SELECT fldPlaylistID, fldName FROM tblPlaylists";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        // Loops through the result set and creates Playlist objects for each row
        while (rs.next())
        {
            Playlist playlist = new Playlist(rs.getString("fldName"));
            playlist.setFldPlaylistID(rs.getInt("fldPlaylistID"));
            // Adds each Playlist object to the list
            playlists.add(playlist);
        }
        // Returns the list of playlists
        return playlists;
    }

    /**
     * Deletes a playlist and its associated songs from the database.
     * @param playlistName the name of the playlist to delete
     * @throws Exception if a database error occurs
     */
    public void deletePlaylist(String playlistName) throws Exception
    {
        // SQL queries to delete playlist songs and the playlist itself
        String deletePlaylistQuery = "DELETE FROM tblPlaylists WHERE fldName = ?";
        String deletePlaylistSongsQuery = "DELETE FROM tblSongsPlaylists WHERE fldPlaylistID = (SELECT fldPlaylistID FROM tblPlaylists WHERE fldName = ?)";

        // Use try-with-resources to manage database connections and statements
        try (Connection connection = getConnection();
             PreparedStatement deleteSongsStatement = connection.prepareStatement(deletePlaylistSongsQuery);
             PreparedStatement deletePlaylistStatement = connection.prepareStatement(deletePlaylistQuery)) {

            // Delete all songs associated with the playlist
            deleteSongsStatement.setString(1, playlistName);
            deleteSongsStatement.executeUpdate();

            // Delete the playlist itself
            deletePlaylistStatement.setString(1, playlistName);
            deletePlaylistStatement.executeUpdate();
        }
    }
}

