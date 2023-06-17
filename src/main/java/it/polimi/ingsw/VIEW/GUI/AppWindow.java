package it.polimi.ingsw.VIEW.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AppWindow {
    @FXML
    public Button button;
    public Stage stage;
    private Parent parent;

    public void ButtonClick(MouseEvent mouseEvent) throws IOException{
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/it.polimi.ingsw/ChatProva.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.setFullScreen(true);
        stage.show();
        System.out.println("check");
    }
}