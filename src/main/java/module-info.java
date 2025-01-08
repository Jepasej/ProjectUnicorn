module org.example.musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires java.desktop;


    opens org.example.musicplayer to javafx.fxml;
    exports org.example.musicplayer;
}