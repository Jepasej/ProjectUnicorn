module org.example.musicplayer {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.musicplayer to javafx.fxml;
    exports org.example.musicplayer;
}