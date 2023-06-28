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
    @FXML
    public TextField score;

    public static GUI gui;

    /******************************************************************************************************************/
    public void itemClick(MouseEvent mouseEvent) {
        if(GUI.controller.getMyTurn()) {
            GUI.cmd.drawItem(mouseEvent);
        }
    }

    public void putClick(MouseEvent mouseEvent) {
        if(GUI.controller.getMyTurn()) {
            if(GUI.gameplayData.selectedCol == -1){
                notification.setText(" SELECT A COLUMN FROM THE BOOKSHELF FIRST! ");
            }else{
                GUI.gameplayData.drawInProgress = false;
                GUI.cmd.putItem();
            }
        }
    }

    public void drawClick(MouseEvent mouseEvent) {
        if(GUI.controller.getMyTurn()){
            GUI.gameplayData.drawInProgress = true;
        }
    }

    public void drawPileClick(MouseEvent mouseEvent) {
        if(GUI.gameplayData.drawInProgress) {
            GUI.gameplayData.selectedItem = new Sprite((ImageView) mouseEvent.getSource());
        }
    }

    public void UpClicked(MouseEvent mouseEvent) {
        if(GUI.gameplayData.drawInProgress) {
            GUI.cmd.drawUp();
        }
    }

    public void DownClicked(MouseEvent mouseEvent) {
        if(GUI.gameplayData.drawInProgress) {
            GUI.cmd.drawDown();
        }
    }

    public void pickCol(MouseEvent mouseEvent){
        if(GUI.gameplayData.drawInProgress){
            GUI.gameplayData.selectedCol = Integer.parseInt(((Button) mouseEvent.getSource()).getId());
        }
    }

    public void setScene() {
        GUI.gameplayData.DrawPile = new StandardSpriteDataStructure().setDrawPile(thirdDraw,secondDraw,firstDraw);
        new StandardSpriteDataStructure().setDrawPileOrder(GUI.gameplayData.drawPileOrder);
        GUI.gameplayData.SpritesBoard = new StandardSpriteDataStructure().setBoard(gridPane);
        GUI.gameplayData.SpriteBookshelf = new StandardSpriteDataStructure().setBookshelf(BookshelfGrid);
        GUI.chatData.chatField = new chatBuilder().standardChat(mes0,mes1,mes2,mes3,mes4,mes5,mes6);
    }

    /************************************************ CHAT ************************************************************/

    public void chatEnter(MouseEvent mouseEvent) {
        if(GUI.chatData.privateMess && GUI.chatData.privateReceiver == null){
            notification.setText(" CLICK THE RECEIVER FIRST ");
        }else{
            GUI.cmd.chatEnter();
        }
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

}
