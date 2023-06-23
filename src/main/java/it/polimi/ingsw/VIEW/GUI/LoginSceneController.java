package it.polimi.ingsw.VIEW.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginSceneController {
    @FXML
    Stage stage;
    @FXML
    public TextField notification;
    @FXML
    public Button loginButton;
    @FXML
    public TextField InputField;
    @FXML
    public TextField InputStatus;

    public static GUI gui;

    public void setLoginScene(MouseEvent mouseEvent) {
        if(!GUI.loginData.loginSceneOpen) {
            GUI.loginData.loginSceneOpen = true;
            while (GUI.notificationBuffer.size() > 0) {
                InputStatus.setText("Insert username");
                if(GUI.notificationBuffer.get(0).equals("FIRST_TO_CONNECT")){
                    GUI.loginData.firstToConnect = true;
                }
                notification.setText(GUI.notificationBuffer.get(0));
                GUI.notificationBuffer.remove(0);
            }
        }
    }

    public void sendLogin(MouseEvent mouseEvent) throws IOException {
        if(GUI.loginData.usernameNotSet){
            GUI.loginData.username = GUI.loginData.stringBuilder.toString();
            GUI.loginData.stringBuilder = new StringBuilder();
            GUI.loginData.usernameNotSet = false;
            InputField.setText(null);
            if(GUI.loginData.firstToConnect){
                InputStatus.setText("Insert Lobby Size");
            }
        }else{
            if(GUI.loginData.lobbySizeNotSet){
                if(GUI.loginData.stringBuilder.toString().length() > 0) {
                    GUI.loginData.lobbySize = Integer.parseInt(GUI.loginData.stringBuilder.toString());
                    GUI.loginData.lobbySizeNotSet = false;
                }
            }
        }
    }

    public void inputKeyLogin(KeyEvent keyEvent) {
        IntegerChecker integerChecker = new IntegerChecker();
        if(gui.getUsernameNotSet()){
            GUI.loginData.stringBuilder.append(keyEvent.getCharacter());
        }else{
            if(integerChecker.check(keyEvent.getCharacter())){
                GUI.loginData.stringBuilder.append(keyEvent.getCharacter());
            }else{
                GUI.loginData.stringBuilder = new StringBuilder();
                InputField.setText(null);
            }
        }
    }

    public void Login(MouseEvent mouseEvent) throws IOException {
        if(GUI.controller.getLoginOK()){
            gui.loadGameScene(mouseEvent);
        }
    }

}
