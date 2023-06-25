package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;
import javafx.scene.input.MouseEvent;

import java.util.List;

public interface GUI_commands {
    void updateGrid(item[][] grid);
    void updateBookshelf( item[][] bookshelf);
    void setCommonGoals(List<Integer> cardID, List<Integer> token);
    void setPersonalGoal(int cardID);
    void setPlayers(List<String> players);
    void setScore(int score);
    void scrollChat(MESSAGE message, boolean Private);
    void chatEnter();
    void drawItem(MouseEvent mouseEvent);
    void drawUp();
    void drawDown();
    void putItem();
    boolean replyDraw();
    boolean replyPut();

}
