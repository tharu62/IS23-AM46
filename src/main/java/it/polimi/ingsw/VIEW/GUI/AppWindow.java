package it.polimi.ingsw.VIEW.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppWindow {
    @FXML
    public javafx.scene.control.Button Button;
    public Stage stage;
    private Parent parent;

    public void ButtonCLick(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/it.polimi.ingsw/LoginScene1Player.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.setFullScreen(true);
        stage.show();

    }
}