package it.polimi.ingsw.VIEW.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/ciao.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/helloscene.fxml"));
        /** loginscene.fxml non funziona perchè il rispettivo controller nel file fxml non esiste. */
        //FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/loginscene.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/playersscene.fxml"));

        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/AppWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        /** getIcons non fa nulla per ora. */
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("/livingroom.png")));

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        //LoginController loginController = fxmlLoader.getController();
        //loginController.setOwnStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}

