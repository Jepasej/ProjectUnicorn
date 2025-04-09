package org.example.frankjavafxhomework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        VBox box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(box,320, 320);

        stage.setTitle("CPR stealer!");
        stage.setScene(scene);
/*
        TextField textField = new TextField();
        textField.setPromptText("DDMMYY-XXXX");
        textField.setOnKeyTyped(event -> {
            if(!Pattern.matches("^(0[1-9]|[12]\\d|3[01])(0[1-9]|1[0-2])(\\d{2})-\\d{4}$", textField.getText())) {
                textField.setStyle("-fx-background-color: red");
            }
            else {
                textField.setStyle(null);
            }
        });
*/
        //box.getChildren().addAll(new CPRLabel(),textField, new CPRLabel(), new CPRTextField());
        //box.getChildren().addAll(new CPRLabel(), new restrictedTextField(TextFieldType.CPR));

        box.getChildren().addAll(new Label("Full Name:"), new restrictedTextField(TextFieldType.TEXT), new Label("Think of a number:"), new restrictedTextField(TextFieldType.NUMBER), new Label("Date:"), new restrictedTextField(TextFieldType.DATE), new Label("CPR-Number:"), new restrictedTextField(TextFieldType.CPR));

        stage.show();

        //thanks bot
        javafx.application.Platform.runLater(box::requestFocus);

    }

    public static void main(String[] args) {
        launch();
    }
}