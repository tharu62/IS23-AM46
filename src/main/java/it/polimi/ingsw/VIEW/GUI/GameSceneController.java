package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.MODEL.GAME;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GameSceneController {

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

    public static GUI gui;


    public void itemClick(MouseEvent mouseEvent) {
        GUI.cmd.selectItemToDraw(mouseEvent);
    }

    public void ButtonCLick(MouseEvent mouseEvent) {

    }

    public void putClick(MouseEvent mouseEvent) {
        GUI.drawInProgress = false;
    }

    public void drawClick(MouseEvent mouseEvent) {
        GUI.drawInProgress = true;
    }

    public void drawPileClick(MouseEvent mouseEvent) {
        GUI.selectedItem = new Sprite((ImageView) mouseEvent.getSource());
    }

    public void UpClicked(MouseEvent mouseEvent) {
        GUI.cmd.drawUp();
    }

    public void DownClicked(MouseEvent mouseEvent) {
        GUI.cmd.drawDown();
    }

    public void setScene(MouseEvent mouseEvent) {
        GUI.DrawPile = new StandardSprite().setDrawPile(t341,t342,t343);
        GUI.SpritesBoard = new StandardSprite().setBoard(gridPane);
        GAME game = new GAME();
        game.space.board.setGrid(4);
        gui.updateGrid(game.space.board.Grid);
    }

    public void chatEnter(MouseEvent mouseEvent) {
        GUI.cmd.chatEnter();
    }

    public void chatClick(MouseEvent mouseEvent) {
        GUI.chatField = new chatBuilder().standardChat(mes0,mes1,mes2,mes3,mes4);
    }

    public void inputKey(KeyEvent keyEvent) {
        if(GUI.chatData.privateMessRec || GUI.chatData.privateMess){
            GUI.chatData.privateStringBuilder.append(keyEvent.getCharacter());
        }
        else{
            GUI.chatData.stringBuilder.append(keyEvent.getCharacter());
        }
    }
}
