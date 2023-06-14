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

        //Image img = new Image("sfondo_parquet.jpg"); //servirebbe l'URL qui
        //ImageInput imginput = new ImageInput();
        //imginput.setSource(img);
        //Group root = new Group();
        //scene = new Scene(root, 1920, 1080);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        //LoginController loginController = fxmlLoader.getController();
        //loginController.setOwnStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}

