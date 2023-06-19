package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CommunicationProtocol;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
     @FXML
     public TextField Username;
     @FXML
     public TextField LobbySize;
    @FXML
    public TextField notification;

    static public List<String> notificationBuffer = new ArrayList<>();
    public int i=0, drawCounter = 0;
    static public boolean usernameNotSet = true, lobbySizeNotSet = true, AppButtonCLicked = false, loginSceneOpen = false;
    static public boolean waiting = false, putInProgress = false, drawInProgress = false;
    static public boolean privateMess = false, privateMessRec = false;
    static public String privateReceiver;
    static public StringBuilder stringBuilder = new StringBuilder();
    static public StringBuilder privateStringBuilder = new StringBuilder();
    static public Sprite selectedItem = new Sprite(null);
    static public Sprite[][] SpritesBoard;
    static public Sprite[] DrawPile = new Sprite[3];
    static public TextField[] chatField = new TextField[5];
    static public Sprite[][] SpriteBookshelf = new Sprite[6][5];
    static public CONTROLLER controller;
    static public CommunicationProtocol com;
    static public CommandsExecutor cmd;

    public void GUI(CONTROLLER controller, ClientRMI client){
        GUI.controller = controller;
        System.out.println("CHECK CONTROLLER OK");
        cmd = new CommandsExecutor(controller, client, this);
    }

    public void GUI(CONTROLLER controller, ClientTCP client){
        GUI.controller = controller;
        if(controller.LoginOK){
            System.out.println("CHECK CONTROLLER OK");
        }
        cmd = new CommandsExecutor(controller, client, this);
    }

    /******************************************************************************************************************/

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/AppWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MY SHELFIE GAME");
        stage.setScene(scene);
        stage.show();
    }

    synchronized public boolean getUsername(){
        return usernameNotSet;
    }

    synchronized public boolean getLobbySize(){
        return lobbySizeNotSet;
    }

    public void ButtonClickAPP(MouseEvent mouseEvent) throws IOException{
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/it.polimi.ingsw/LoginSceneFirstPlayer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        loginSceneOpen = true;
    }

    public void ButtonCLick(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/it.polimi.ingsw/StandardGameScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateGrid(item[][] grid){
        cmd.updateGrid(grid);
    }

    private void updateDrawPile(){
        //
    }

    public void putClick(MouseEvent mouseEvent) {
        drawInProgress = false;
    }

    public void drawClick(MouseEvent mouseEvent) {
        drawInProgress = true;
    }

    public void itemClick(MouseEvent mouseEvent) {
        cmd.selectItemToDraw(mouseEvent);
    }

    public void UpClicked(MouseEvent mouseEvent) {
        cmd.drawUp();
    }

    public void DownClicked(MouseEvent mouseEvent) {
        cmd.drawDown();
    }

    public void drawPileClick(MouseEvent mouseEvent) {
        selectedItem = new Sprite((ImageView) mouseEvent.getSource());
    }

    public void inputKey(KeyEvent keyEvent) {
        if(privateMessRec || privateMess){
            privateStringBuilder.append(keyEvent.getCharacter());
        }
        else{
            stringBuilder.append(keyEvent.getCharacter());
        }
    }

    public void chatClick(MouseEvent mouseEvent) {
        chatField = new chatBuilder().standardChat(mes0,mes1,mes2,mes3,mes4);
    }

    synchronized public void scrollChat(String text, boolean Private){
        //TODO
        MESSAGE mess = new MESSAGE();
        mess.text = text;
        cmd.scrollChat(mess, Private);
    }

    public void chatEnter(MouseEvent mouseEvent) {
        cmd.chatEnter();
    }

    public void privateChatEnter(MouseEvent mouseEvent) {
        chatInput.setPromptText("Insert receiver");
        privateMessRec = true;
    }

    public void setScene(MouseEvent mouseEvent) {
        DrawPile = new StandardSprite().setDrawPile(t341,t342,t343);
        SpritesBoard = new StandardSprite().setBoard(gridPane);
        GAME game = new GAME();
        game.space.board.setGrid(4);
        updateGrid(game.space.board.Grid);
    }

    public void ButtonClick(MouseEvent mouseEvent) {

    }

    public void setCommonGoals(int[] cardsID){
        cmd.setCommonGoals();
    }

    public void setPersonalGoal(int cardID){
        cmd.setPersonalGoal();
    }

    public void sendUsername(MouseEvent mouseEvent) {
        if(controller.LoginOK){
            /**
             * if(check input valid)
             */
            usernameNotSet = false;
        }
        if(notificationBuffer.size() > 0){
            notification.setText(notificationBuffer.get(0));
        }
    }

    public void sendLobbySize(MouseEvent mouseEvent) {
        /**
         * if(check input valid)
         */
        lobbySizeNotSet = false;
    }

    public void setNotification(String text){
        notificationBuffer.add(text);
    }

    public synchronized boolean getAppButtonClicked(){
        return AppButtonCLicked;
    }

}

