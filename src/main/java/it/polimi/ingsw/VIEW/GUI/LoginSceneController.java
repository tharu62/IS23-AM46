package it.polimi.ingsw.VIEW.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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


    public static GUI gui;
    public void setLoginScene(MouseEvent mouseEvent) {
        if(!GUI.loginData.loginSceneOpen) {
            GUI.loginData.loginSceneOpen = true;
            while (GUI.notificationBuffer.size() > 0) {
                InputField.setPromptText("Insert username");
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
            GUI.loginData.username = GUI.chatData.stringBuilder.toString();
            GUI.chatData.stringBuilder = new StringBuilder();
            GUI.loginData.usernameNotSet = false;
            InputField.setText(null);
            if(GUI.loginData.firstToConnect){
                InputField.setPromptText("Insert Lobby Size");
            }
        }else{
            if(GUI.loginData.firstToConnect){
                GUI.loginData.lobbySize = Integer.parseInt(GUI.chatData.stringBuilder.toString());
                GUI.loginData.lobbySizeNotSet = false;
            }else{
                if(!GUI.controller.LoginOK){
                    GUI.loginData.username = GUI.chatData.stringBuilder.toString();
                    GUI.chatData.stringBuilder = new StringBuilder();
                    InputField.setText(null);
                }
            }
        }
    }

    public void inputKeyLogin(KeyEvent keyEvent) {
        if(!GUI.controller.LoginOK){
            if(gui.getUsernameNotSet()){
                GUI.chatData.stringBuilder.append(keyEvent.getCharacter());
            }else{
                if(new IntegerChecker().check(keyEvent.getCharacter())){
                    GUI.chatData.stringBuilder.append(keyEvent.getCharacter());
                }
            }
        }
    }

    public void Login(MouseEvent mouseEvent) throws IOException {
        if(GUI.controller.getLoginOK()){
            gui.loadGameScene();
        }
    }

}
