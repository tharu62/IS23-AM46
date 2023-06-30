package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.*;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.VIEW.RMI;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class GUI extends Application {
    @FXML
    public Stage stage;
    public static Scene gameScene;
    public static Scene loginScene;
    public static LoginData loginData = new LoginData();
    public static ChatData chatData = new ChatData();
    public static GameplayData gameplayData = new GameplayData();
    public static LoginSceneController loginSceneController;
    public static GameSceneController gameSceneController;

    public static CONTROLLER controller;
    public static CommandsExecutor cmd;

    /**
     * This method start the AppWindow and Login scene and connects the references of this GUI, and it's controller to
     * the Login scene. ( AppWindow's javafx controller is set to GUI by default )
     * In case the Player has started the Application with the RMI protocol, this method also starts the client RMI
     * in a new thread.
     *
     * @param stage is stage that javafx requires to set the scene on screen.
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/AppWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MY SHELFIE GAME");
        stage.setScene(scene);
        stage.show();

        fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/LoginScene.fxml"));
        loginScene = new Scene(fxmlLoader.load());
        loginSceneController = fxmlLoader.getController();
        LoginSceneController.gui = this;
        loginData.loginSceneOpen = true;

        if(CommandsExecutor.com instanceof RMI){
            Task<Void> task = new Task<>() {
                @Override public Void call() throws Exception {
                    CommandsExecutor.com.startClientRMI();
                    return null;
                }
            };
            new Thread(task).start();
        }

    }

    /**
     * This method shows the Login scene, made in the start() method, on screen.
     * The method also starts the Gameplay scene and connects it with the reference to the GUI and the controller.
     * Various messages are set in the Gameplay depending on the status of the game.
     *
     * @param mouseEvent
     * @throws IOException
     */
    public void loadLoginScene(MouseEvent mouseEvent) throws IOException{
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();

        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/StandardGameScene.fxml"));
        gameScene = new Scene(fxmlLoader.load());
        gameSceneController = fxmlLoader.getController();
        GameSceneController.gui = this;
        gameSceneController.setScene();
        gameplayData.gameSceneOpen = true;
        if(!controller.notConnected){
            if(controller.firstToConnect){
                loginData.firstToConnect = true;
                loginSceneController.notification.setText("FIRST_TO_CONNECT");
                loginSceneController.InputStatus.setText("Insert username");
            }else{
                loginSceneController.InputStatus.setText("Insert username");
                loginSceneController.notification.setText("CONNECTED");
            }
        }else{
            loginSceneController.notification.setText("NOT_CONNECTED");
        }

    }

    /**
     * This method shows the Gameplay scene, made in the loadLoginScene() method, on screen.
     *
     * @param mouseEvent
     */
    public void loadGameScene(MouseEvent mouseEvent) {
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(gameScene);
        stage.show();
        loginData.loginSceneOpen = false;
        if(controller.getMyTurn()) {
            gameSceneController.notification.setText("IT_IS_YOUR_TURN");
        }else{
            gameSceneController.notification.setText("waiting for notification...");
        }
    }


    public void updateGrid(item[][] grid){
        cmd.updateGrid(grid);
    }

    public void updateCommonGoals(List<Integer> cardID, List<Integer> token){
        cmd.setCommonGoals(cardID, token);
    }

    public void updatePersonalGoal(int cardID){
        cmd.setPersonalGoal(cardID);
    }

    public void updateBookshelf( item[][] bookshelf){
        cmd.updateBookshelf(bookshelf);
    }

    public void setPlayers(List<String> players){
        cmd.setPlayers(players);
    }

    public void setScore(int score){
        cmd.setScore(score);
    }

    /**
     * This method scrolls the chat messages according to the length set as a predefined value.
     * A new message moves all the messages one position upwards in the chat, then if the message length is greater than
     * the threshold, the message is split in various messages  and the chat is scrolled according to the number of
     * messages resulting from the split. ( default threshold : 45 char )
     *
     * @param message
     */
    synchronized public void scrollChat(MESSAGE message){
        StringAdapter stringAdapter = new StringAdapter();
        if(message.header[1].equals(controller.username)){
                stringAdapter.splitPrivate(this, message, 45);
        }else{
            stringAdapter.splitPublic(this, message, 45);
        }

    }

    /**
     * This method shows the message given in the notification TextField in the Login scene and / or in the Gameplay scene.
     *
     * @param message is the message received.
     */
    public void Notify(String message){
        if(gameSceneController != null){
            if(gameSceneController.notification != null){
                gameSceneController.notification.setText(message);
            }
        }
        if(loginSceneController != null){
            if(loginSceneController.notification != null){
                loginSceneController.notification.setText(message);
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

