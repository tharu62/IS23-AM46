package it.polimi.ingsw.VIEW.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LoginSceneController {
    @FXML
    public TextField notification;
    @FXML
    public TextField InputField;
    @FXML
    public TextField InputStatus;

    public static GUI gui;

    public void sendLogin(MouseEvent mouseEvent){
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

    public void Login(MouseEvent mouseEvent) {
        if(GUI.controller.getLoginOK()){
            gui.loadGameScene(mouseEvent);
            GUI.gameplayData.gameSceneOpen = true;
        }
    }

}
