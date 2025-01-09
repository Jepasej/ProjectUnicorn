package org.example.musicplayer;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 * Preparing the system to write in our Database
 * and establish connection to the DB
 */
public class DBConnection {
    //initializes variables for the getConnection method.
    static final String URL = "jdbc:sqlserver://localhost;portNumber=1433;databaseName=DbMusicPlayer";
    static final String USERNAME = "sa"; // replace with your username
    static final String PASSWORD = "admin"; // replace with your password

    //connects us to the SQL server named DbMusicPlayer
    public static Connection getConnection() throws Exception {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conn;
    }

    public static void dbConnection(String args[]) throws Exception {
        //Connects us to the SQL server, when we start the program.
        System.out.println("Connecting to server.");
        Connection conn = getConnection();
    }
}

