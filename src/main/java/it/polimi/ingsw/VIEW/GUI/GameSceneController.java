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


    /**
     * This method check if the clicked item can be drawn only if it's the players turn.
     *
     * @param mouseEvent is the event handler need to get the fxid of the item clicked.
     */
    public void itemClick(MouseEvent mouseEvent) {
        if(GUI.controller.getMyTurn()) {
            GUI.cmd.drawItem(mouseEvent);
        }
    }

    /**
     * This method checks if the item in the drawPile can be placed in the bookshelf by sending a request to the server.
     * The request is sent if the input is reasonable => drawPile not empty and a column in the bookshelf have been
     * selected.
     *
     * @param mouseEvent si not used.
     */
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

    /**
     * This method start the draw process for the player if it's the players turn.
     *
     * @param mouseEvent is not used.
     */
    public void drawClick(MouseEvent mouseEvent) {
        if(GUI.controller.getMyTurn()){
            GUI.gameplayData.drawInProgress = true;
        }
    }

    /**
     * This method saves the selected item in the draPile if the draw process is in progress.
     *
     * @param mouseEvent is not used.
     */
    public void drawPileClick(MouseEvent mouseEvent) {
        if(GUI.gameplayData.drawInProgress) {
            GUI.gameplayData.selectedItem = new Sprite((ImageView) mouseEvent.getSource());
        }
    }

    /**
     * This method moves the selected item in the drawPile by one position (upward).
     *
     * @param mouseEvent is not used.
     */
    public void UpClicked(MouseEvent mouseEvent) {
        if(GUI.gameplayData.drawInProgress) {
            GUI.cmd.drawUp();
        }
    }

    /**
     * This method moves the selected item in the drawPile by one position (downward).
     *
     * @param mouseEvent is not used.
     */
    public void DownClicked(MouseEvent mouseEvent) {
        if(GUI.gameplayData.drawInProgress) {
            GUI.cmd.drawDown();
        }
    }

    /**
     *  This method saves the selected column of the bookshelf.
     *
     * @param mouseEvent is needed to get the unique fxid of the button clicked.
     *                   Each fxid has a corresponding column assigned.
     */
    public void pickCol(MouseEvent mouseEvent){
        if(GUI.gameplayData.drawInProgress){
            GUI.gameplayData.selectedCol = Integer.parseInt(((Button) mouseEvent.getSource()).getId());
        }
    }

    /**
     * This method is used to set the main scene of the game.
     * the method initialize all the data structure that the javafx scene use as reference to show the correct Image on
     * screen.
     * For example : DrawPile is initialized with the fx-ids of the corresponding ImageView on the scene.
     *
     */
    public void setScene() {
        GUI.gameplayData.DrawPile = new StandardSpriteDataStructure().setDrawPile(thirdDraw,secondDraw,firstDraw);
        new StandardSpriteDataStructure().setDrawPileOrder(GUI.gameplayData.drawPileOrder);
        GUI.gameplayData.SpritesBoard = new StandardSpriteDataStructure().setBoard(gridPane);
        GUI.gameplayData.SpriteBookshelf = new StandardSpriteDataStructure().setBookshelf(BookshelfGrid);
        GUI.chatData.chatField = new chatBuilder().standardChat(mes0,mes1,mes2,mes3,mes4,mes5,mes6);
    }

    /************************************************ CHAT ************************************************************/

    /**
     * This method sends the message saved in the GUI to the server. If the message is private a receiver must be
     * selected before invoking the method, otherwise the method notifies the player that the receiver haven't been
     * selected.
     *
     * @param mouseEvent is not used.
     */
    public void chatEnter(MouseEvent mouseEvent) {
        if(GUI.chatData.privateMess && (GUI.chatData.privateReceiver == null || GUI.chatData.privateReceiver.equals(""))){
            notification.setText(" CLICK THE RECEIVER FIRST ");
        }else{
            GUI.cmd.chatEnter();
        }
    }

    /**
     * This method start the private message process. Once clicked the player must send a private message to turn back
     * at sending public messages.
     *
     * @param mouseEvent is not used.
     */
    public void privateChatEnter(MouseEvent mouseEvent) {
        chatInput.setPromptText("Insert receiver");
        GUI.chatData.privateMess = true;
    }

    /**
     * This method saves the chat input from the player in the GUI.
     *
     * @param keyEvent is not used.
     */
    public void inputKey(KeyEvent keyEvent) {
        GUI.chatData.chatString = chatInput.getText();
    }

    /**
     * This method saves the receiver for a private message in the GUI.
     * The method works only if the private message process has started.
     *
     * @param mouseEvent is needed to get the fx-id of the selected TextField corresponding to a player username.
     */
    public void privateReceiverClicked(MouseEvent mouseEvent) {
        if(GUI.chatData.privateMess){
            String receiver = ((TextField) mouseEvent.getSource()).getText();
            if( receiver != null ){
                GUI.chatData.privateReceiver = receiver;
            }
        }
    }

}
