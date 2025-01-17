package org.example.musicplayer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 * Preparing the system to write in our Database
 * and establish connection to the DB
 */
public class DBConnection
{
    //initializes variables for the getConnection method.
    static final String URL = "jdbc:sqlserver://localhost;portNumber=1433;databaseName=DbMusicPlayer;TrustServerCertificate=true;";
    static final String USERNAME = "sa"; // replace with your username
    static final String PASSWORD = "admin"; // replace with your password

    //connects us to the SQL to DB server
    public static Connection getConnection() throws Exception {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conn;
}

    /**
     * Initializes an arraylist with data from tblSongs from our database
     * @return returns all data from the database
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

        boolean hasSongs = false;

        //Loops through every song until none left and retrieves all information about the songs from out DB.
        while (rs.next()) {
            hasSongs = true;
            Song song = new Song();
            song.setFldSongID(rs.getInt(1));
            song.setFldName(rs.getString(2).trim());
            song.setFldLengthInSeconds(rs.getString(3).trim());
            song.setFldArtist(rs.getString(4).trim());
            song.setFldAlbum(rs.getString(5).trim());
            song.setFldFilePath(rs.getString(6).trim());
            songs.add(song);
        }
        if (!hasSongs) {
            System.out.println("No songs found.");
        }
        return songs;
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
        while (rs.next()) {
            hasSongs = true;
            Song song = new Song();
            song.setFldSongID(rs.getInt(1));
            song.setFldName(rs.getString(2).trim());
            song.setFldLengthInSeconds(rs.getString(3).trim());
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
}

