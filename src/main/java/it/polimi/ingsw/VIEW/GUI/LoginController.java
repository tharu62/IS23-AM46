package it.polimi.ingsw.VIEW.GUI;


/*
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.SocketException;



public class LoginController implements Initializable {
    private Stage stage;
    private Scene scene;
    private FXMLLoader gameloader;
    private Parent parent;



    //fxml variables
    @FXML
    private SubScene SubScene;
    @FXML
    private TextField nickname;
    @FXML
    private TextField ip;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Label waiting_communication;

    private void sendLogInRequest() throws SocketException {
        if(already_connected){
            Action act = new Action();
            act.setUsername(nickname.getText());
            msg.send(act);
        } else {
            //Creating the action
            Action act = new Action();
            act.setGamePhase(GamePhase.START);
            act.setUsername(nickname.getText());
            act.LobbySize(Integer.parseInt(choiceBox.getValue()));
            //Initializing the game controller
            GameGraphicController.username = act.getUsername();
            GameGraphicController.LobbySize = act.getLobbySize();
            GameGraphicController.msg = msg;
            gameloader = new FXMLLoader(GUI.class.getResource("gameGraphic.fxml"));
            try {
                gameparent = gameloader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            game_controller = gameloader.getController();
            //Sending the message
            msg.send(act);
            last_sent = act.getGamePhase();
            already_connected = true;
        }
    }

    public void switchScene(ActionEvent event) throws IOException {
        if(!already_connected)
            initializeNetwork();
        PopUpLauncher alert = new PopUpLauncher();
        String input_user = nickname.getText();
        if(InputUtils.isNullOrEmpty(input_user)){
            alert.setTitle("Bad request");
            alert.setMessage("Username can not be empty");
            alert.show();
            return;
        } else {
            sendLogInRequest();
        }

        showWaitingScene(true);
        waiting_communication.setText("You're being connected to " + msg.getUsername() + "'s lobby");
    }

    private void showWaitingScene(boolean visible){
        SubScene.setVisible(visible);
        waiting_communication.setVisible(visible);
    }


}

*/