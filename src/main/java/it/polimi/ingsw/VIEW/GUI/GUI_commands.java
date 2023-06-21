package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;
import javafx.scene.input.MouseEvent;

public interface GUI_commands {
    void updateGrid(item[][] grid);
    void updateBookshelf();
    void scrollChat(MESSAGE message, boolean Private);
    void chatEnter();
    void drawUp();
    void drawDown();
    void selectItemToDraw(MouseEvent mouseEvent);
    void putDrawInBookshelf();
    void setCommonGoals();
    void setPersonalGoal();
    void login();
    boolean replyDraw();
    boolean replyPut();

}
