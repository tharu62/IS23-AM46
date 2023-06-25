package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public interface GameInterface {
    void notifyInterface(String message);
    String getUsername(CONTROLLER controller);
    int getLobbySize(CONTROLLER controller);
    void receiveChat(CONTROLLER controller, MESSAGE message);
    void startInterface(String[] args);
    void setPlayers(CONTROLLER controller, List<String> players);
    void setBoard(CONTROLLER controller, item[][] grid);
    void setCommonGoals(CONTROLLER controller, List<Integer> cardID, List<Integer> token);
    void setPersonalGoal(CONTROLLER controller, int cardID);
    void setBookshelf(CONTROLLER controller, item[][] bookshelf);
    void setScore(CONTROLLER controller, int score);
    void setPlayerToPlay(CONTROLLER controller, String ptp);

}
