package it.polimi.ingsw.CONTROLLER_SCENE;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main
{
        public void start(Stage stage)
        {
            try
            {
                Parent root= FXMLLoader.load(getClass().getResource("HelloScene0.fxml"));
                Scene scene= new Scene(root);
                stage.getScene();
                stage.show();

            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

