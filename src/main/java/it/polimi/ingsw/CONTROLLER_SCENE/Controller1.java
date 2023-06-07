

package it.polimi.ingsw.CONTROLLER_SCENE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;





public class Controller1
{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private EventObject event;


    public void helloscene(ActionEvent actionEvent) throws IOException {
        root= FXMLLoader.load(getClass().getResource("HelloScene0.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void loginScene(ActionEvent actionEvent) throws IOException {
        root= FXMLLoader.load(getClass().getResource("LoginScene0.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
