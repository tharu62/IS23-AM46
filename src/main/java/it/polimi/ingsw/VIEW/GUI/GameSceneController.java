package it.polimi.ingsw.VIEW.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GameSceneController {

    @FXML
    public GridPane gridPane;
    @FXML
    public GridPane BookshelfGrid;
    @FXML
    public ImageView firstDraw;
    @FXML
    public ImageView secondDraw;
    @FXML
    public ImageView thirdDraw;
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
    public TextField mes5;
    @FXML
    public TextField mes6;
    @FXML
    public TextField chatInput;
    @FXML
    public ImageView commonGoal1;
    @FXML
    public ImageView commonGoal2;
    @FXML
    public ImageView personalGoal;
    @FXML
    public TextField notification;
    @FXML
    public TextField player_0;
    @FXML
    public TextField player_1;
    @FXML
    public TextField player_2;
    @FXML
    public TextField player_3;
    @FXML
    public ImageView scoreToken0;
    @FXML
    public ImageView scoreToken1;
    @FXML
    public ImageView scoreToken2;
    public boolean sceneNotSet = true;
    public static GUI gui;

    /******************************************************************************************************************/
    public void itemClick(MouseEvent mouseEvent) {
        if(GUI.controller.getMyTurn()) {
            GUI.cmd.selectItemToDraw(mouseEvent);
        }
    }

    public void putClick(MouseEvent mouseEvent) {
        if(GUI.controller.getMyTurn()) {
            GUI.gameplayData.drawInProgress = false;
            if(GUI.gameplayData.selectedCol == -1){
                gui.setNotification(" SELECT A COLUMN FROM THE BOOKSHELF FIRST! ");
            }else{
                GUI.cmd.putDrawInBookshelf();
            }
        }
    }

    public void drawClick(MouseEvent mouseEvent) {
        if(GUI.controller.getMyTurn()){
            GUI.gameplayData.drawInProgress = true;
        }
    }

    public void drawPileClick(MouseEvent mouseEvent) {
        GUI.gameplayData.selectedItem = new Sprite((ImageView) mouseEvent.getSource());
    }

    public void UpClicked(MouseEvent mouseEvent) {
        GUI.cmd.drawUp();
    }

    public void DownClicked(MouseEvent mouseEvent) {
        GUI.cmd.drawDown();
    }

    public void pickCol(MouseEvent mouseEvent){
        if(GUI.gameplayData.drawInProgress){
            GUI.gameplayData.selectedCol = Integer.parseInt(((Button) mouseEvent.getSource()).getId());
        }
    }

    public void setScene() {
        GUI.gameplayData.DrawPile = new StandardSprite().setDrawPile(firstDraw,secondDraw,thirdDraw);
        GUI.gameplayData.SpritesBoard = new StandardSprite().setBoard(gridPane);
        GUI.gameplayData.SpriteBookshelf = new StandardSprite().setBookshelf(BookshelfGrid);
        GUI.chatData.chatField = new chatBuilder().standardChat(mes0,mes1,mes2,mes3,mes4,mes5,mes6);
        gui.updateGrid(GUI.controller.grid);
        player_0.setText(GUI.controller.players.get(0));
        player_0.setText(GUI.controller.players.get(1));
        if (GUI.controller.players.size() > 2) {
            player_0.setText(GUI.controller.players.get(2));
            if (GUI.controller.players.size() > 3) {
                player_0.setText(GUI.controller.players.get(3));
            }
        }
        GUI.cmd.setCommonGoals();
    }

    public void updateScene(){
        if(sceneNotSet){
            setScene();
            sceneNotSet = false;
        }
        gui.updateGrid(GUI.controller.grid);
        GUI.gameplayData.DrawPile = new StandardSprite().setDrawPile(firstDraw,secondDraw,thirdDraw);
        GUI.gameplayData.drawCounter = 0;
        //score
    }

    /************************************************ CHAT ************************************************************/

    public void chatEnter(MouseEvent mouseEvent) {
        GUI.cmd.chatEnter();
    }

    public void privateChatEnter(MouseEvent mouseEvent) {
        chatInput.setPromptText("Insert receiver");
        GUI.chatData.privateMess = true;
    }

    public void inputKey(KeyEvent keyEvent) {
        if(GUI.chatData.privateMess){
            GUI.chatData.privateStringBuilder.append(keyEvent.getCharacter());
        }
        else{
            GUI.chatData.stringBuilder.append(keyEvent.getCharacter());
        }
    }

    public void privateReceiverClicked(MouseEvent mouseEvent) {
        if(GUI.chatData.privateMess){
            String receiver = ((TextField) mouseEvent.getSource()).getText();
            if( receiver != null ){
                GUI.chatData.privateReceiver = receiver;
            }
        }
    }

    public void pclick(MouseEvent mouseEvent) {
        GUI.cmd.setPersonalGoal();
    }
}
