package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.*;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI extends Application{
    @FXML
    public Stage stage;
    public static List<String> notificationBuffer = new ArrayList<>();
    public static LoginData loginData = new LoginData();
    public static ChatData chatData = new ChatData();
    public static GameplayData gameplayData = new GameplayData();
    public static LoginSceneController loginSceneController;
    public static GameSceneController gameSceneController;
    public static GuiLoginHandler guiLoginHandler = new GuiLoginHandler();
    public static boolean loginHandlerNotActive = true;

    public static CONTROLLER controller;
    public static CommandsExecutor cmd;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/AppWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MY SHELFIE GAME");
        stage.setScene(scene);
        stage.show();
    }

    public void loadLoginScene(MouseEvent mouseEvent) throws IOException{
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/LoginScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        loginSceneController = fxmlLoader.getController();
        loginData.loginSceneOpen = true;
        LoginSceneController.gui = this;
    }

    public void loadGameScene(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/StandardGameScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        loginData.loginSceneOpen = false;
        gameplayData.gameSceneOpen = true;
        gameSceneController = fxmlLoader.getController();
        GameSceneController.gui = this;
    }

    public void updateGrid(item[][] grid){
        cmd.updateGrid(grid);
    }

    synchronized public void scrollChat(MESSAGE message, boolean Private){
        //TODO
        if(gameSceneController != null){
            cmd.scrollChat(message, Private);
        }else{
            chatData.chatBuffer.add(message);
        }
    }

    public void setNotification(String message){
        if(gameplayData.gameSceneOpen) {
            gameSceneController.notification.setText(message);
            if (message.equals("                                 IT IS YOUR TURN                                          ")) {
                gameSceneController.updateScene();
            }
            if (message.equals("                                IT IS NOT YOUR TURN                                       ")) {
                gameSceneController.updateScene();
            }
            if (message.equals("                                        LAST ROUND                                        ")) {
                gameSceneController.scoreToken0.setImage(null);
            }
        }else{
            notificationBuffer.add(message);
            if(loginHandlerNotActive){
                loginHandlerNotActive = false;
                GuiLoginHandler.gui = this;
                guiLoginHandler.start();
            }
        }
    }

    public void setLoginNotification(String message){
        if(loginData.loginSceneOpen){
            if(message.equals("REPLY_NOT_ACCEPTED")){
                loginData.usernameNotSet = true;
                loginData.lobbySizeNotSet = true;
            }
            loginSceneController.notification.setText(message);
        }else{
            notificationBuffer.add(message);
            if(loginHandlerNotActive){
                loginHandlerNotActive = false;
                GuiLoginHandler.gui = this;
                guiLoginHandler.start();
            }
        }
    }

    synchronized public boolean getUsernameNotSet(){
        return loginData.usernameNotSet;
    }

    synchronized public boolean getLobbySizeNotSet(){
        return loginData.lobbySizeNotSet;
    }

    public void main(String[] args){
        launch();
    }
}

