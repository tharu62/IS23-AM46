package it.polimi.ingsw.VIEW.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppWindow extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it.polimi.ingsw/LoginScene.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("MY SHELFIE GAME");
        stage.setScene(scene);
        stage.show();
    }
}