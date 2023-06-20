package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.*;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.NETWORK.Settings;
import it.polimi.ingsw.SetUp;
import it.polimi.ingsw.TCP.ClientTCP;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI extends Application{
    @FXML
    public Stage stage;
    @FXML
    public GridPane gridPane;
    @FXML
    public ImageView t341;
    @FXML
    public ImageView t342;
    @FXML
    public ImageView t343;
    @FXML
    public TextField mes0;
    @FXML
    public TextField mes1;
    @FXML
    public TextField mes2;
    @FXML
    public TextField mes3;
    @FXML
    public TextField mes4;
    @FXML
    public TextField chatInput;
    @FXML
    public ImageView commonGoal1;
    @FXML
    public ImageView commonGoal2;
    @FXML
    public ImageView personalGoal;

    public static int i=0, drawCounter = 0;
    public static List<String> notificationBuffer = new ArrayList<>();
    public static LoginData loginData = new LoginData();
    public static ChatData chatData = new ChatData();
    public static boolean waiting = false, putInProgress = false, drawInProgress = false;
    public static Sprite selectedItem = new Sprite(null);
    public static Sprite[][] SpritesBoard;
    public static Sprite[] DrawPile = new Sprite[3];
    public static TextField[] chatField = new TextField[5];
    public static Sprite[][] SpriteBookshelf = new Sprite[6][5];
    public static LoginSceneController loginSceneController;
    public static GameSceneController gameSceneController;
    public static SetUp setUp;
    public static CONTROLLER controller;
    public static CommunicationProtocol com;
    public static CommandsExecutor cmd;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        this.stage = stage;
        if(setUp.selectedTCP){
            ClientTCP client = new ClientTCP(Settings.PORT_TCP, setUp.disconnected);
            controller = new CONTROLLER(Connection.TCP , client, interfaceType.GUI);
            CONTROLLER.gui = this;
            controller.Interface = new guiHandler(this, controller, client);
            client.controller = controller;
            client.start();
        }
        if(setUp.selectedRMI){
            ClientTCP client = new ClientTCP(Settings.PORT_TCP, setUp.disconnected);
            controller = new CONTROLLER(Connection.TCP , client, interfaceType.GUI);
            CONTROLLER.gui = this;
            controller.Interface = new guiHandler(this, controller, client);
            client.controller = controller;
            client.start();
        }

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
        LoginSceneController.gui = this;
    }

    public void loadGameScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/StandardGameScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        gameSceneController = fxmlLoader.getController();
        GameSceneController.gui = this;
    }

    public void updateGrid(item[][] grid){
        cmd.updateGrid(grid);
    }

    synchronized public void scrollChat(String text, boolean Private){
        //TODO
        MESSAGE mess = new MESSAGE();
        mess.text = text;
        cmd.scrollChat(mess, Private);
    }

    public void privateChatEnter(MouseEvent mouseEvent) {
        chatInput.setPromptText("Insert receiver");
        chatData.privateMessRec = true;
    }

    public void setScene(MouseEvent mouseEvent) {
        DrawPile = new StandardSprite().setDrawPile(t341,t342,t343);
        SpritesBoard = new StandardSprite().setBoard(gridPane);
        GAME game = new GAME();
        game.space.board.setGrid(4);
        updateGrid(game.space.board.Grid);
    }

    public void setCommonGoals(int[] cardsID){
        cmd.setCommonGoals();
    }

    public void setPersonalGoal(int cardID){
        cmd.setPersonalGoal();
    }

    public void setNotification(String text){
        if(loginData.loginSceneOpen){
            loginSceneController.notification.setText(text);
        }else{
            notificationBuffer.add(text);
        }
    }

    synchronized public boolean getUsernameNotSet(){
        return loginData.usernameNotSet;
    }

    synchronized public boolean getLobbySizeNotSet(){
        return loginData.lobbySizeNotSet;
    }

}

