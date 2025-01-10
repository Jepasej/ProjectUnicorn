package org.example.musicplayer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * Preparing the system to write in our Database
 * and establish connection to the DB
 */
public class DBConnection {
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


    public void readSongsForStart() throws Exception{
        String sql = "SELECT fldName, fldArtist, fldLengthInSeconds FROM tblSongs";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        boolean hasSongs = false;
        while (rs.next()) {
            hasSongs = true;
            Song song = new Song();
            song.setFldName(rs.getString(1));
            song.setFldArtist(rs.getString(2));
            song.setFldLengthInSeconds(rs.getString(3));
            System.out.println(song.getFldName().trim()+" "+ song.getFldArtist().trim()+" "+ song.getFldLengthInSeconds().trim());
        }
        if (!hasSongs) {
            System.out.println("No songs found.");
        }
    }
}

