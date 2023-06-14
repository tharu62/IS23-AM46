

package it.polimi.ingsw.CONTROLLER_SCENE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;

public class Controller1
{
    @FXML
    public TextField Username;
    @FXML
    public TextField LobbySize;
    public int lobbySizeINT;
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

    /**
     * This method uses the values stored in the "username" and "LobbySize" TextField for the Login.
     * @param mouseEvent
     */
    public void LoginSend(MouseEvent mouseEvent) {
        lobbySizeINT = Integer.parseInt(LobbySize.getText());
        System.out.println(lobbySizeINT);
    }

}
