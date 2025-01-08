module org.example.musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.musicplayer to javafx.fxml;
    exports org.example.musicplayer;
}