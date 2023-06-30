package it.polimi.ingsw.VIEW.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class LoginSceneController {
    @FXML
    public TextField notification;
    @FXML
    public TextField InputField;
    @FXML
    public Text InputStatus;

    public static GUI gui;

    /**
     * This method allow the player to send his input to the server, if and only if the server has issued a request for
     * player input. ( if a request for input is not send from the server, this method does nothing )
     *
     * @param mouseEvent
     */
    public void sendLogin(MouseEvent mouseEvent){
        if(GUI.loginData.usernameNotSet){
            //GUI.loginData.username = GUI.loginData.stringBuilder.toString();
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
        if(GUI.controller.getLoginOK()){
            gui.loadGameScene(mouseEvent);
            GUI.gameplayData.gameSceneOpen = true;
        }
    }

    /**
     * This method saves the input of the player in the GUI according to the request sent by server.
     * If the request is the username -> loginData.username;
     * If the request is the lobbySIze -> loginData.stringBuilder;
     *
     * @param keyEvent is the key typed.
     */
    public void inputKeyLogin(KeyEvent keyEvent) {
        IntegerChecker integerChecker = new IntegerChecker();
        if(gui.getUsernameNotSet()){
            //GUI.loginData.stringBuilder.append(keyEvent.getCharacter());
            GUI.loginData.username = InputField.getText();
        }else{
            if(integerChecker.check(keyEvent.getCharacter())){
                GUI.loginData.stringBuilder.append(keyEvent.getCharacter());
            }else{
                GUI.loginData.stringBuilder = new StringBuilder();
                InputField.setText(null);
            }
        }
    }

}
