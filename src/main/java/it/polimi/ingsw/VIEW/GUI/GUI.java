package it.polimi.ingsw.VIEW.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/AppWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MY SHELFIE GAME");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

