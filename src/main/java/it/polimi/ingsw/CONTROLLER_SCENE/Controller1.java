

package it.polimi.ingsw.CONTROLLER_SCENE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;

public class Controller1
{
    @FXML
    public Label Login;
    public Label Username;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private EventObject event;


    public void helloscene(ActionEvent actionEvent) throws IOException {
        root= FXMLLoader.load(getClass().getResource("/it.polimi.ingsw/HelloScene0.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void loginScene(ActionEvent actionEvent) throws IOException {
        root= FXMLLoader.load(getClass().getResource("/it.polimi.ingsw/LoginScene0.fxml"));
        event = new EventObject(root);
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void LoginSend(MouseEvent mouseEvent) {

    }
}
