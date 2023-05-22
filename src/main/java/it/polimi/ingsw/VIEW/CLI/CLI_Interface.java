package it.polimi.ingsw.VIEW.CLI;

import it.polimi.ingsw.MODEL.COMMON_GOAL_CARD;
import it.polimi.ingsw.MODEL.PERSONAL_GOAL_CARD;
import it.polimi.ingsw.MODEL.item;

import java.rmi.RemoteException;
import java.util.List;

public interface CLI_Interface {

    void notify(String message);
    String getUsername();
    int getLobbySize();
    void printBoard(item[][] grid);
    void printPersonalGoal(PERSONAL_GOAL_CARD personalGoalCard);
    void printCommonGoals(List<COMMON_GOAL_CARD> commonGoalCards);
    void sendChat() throws RemoteException;
    void printActions();
}
