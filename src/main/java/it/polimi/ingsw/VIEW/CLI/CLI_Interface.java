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
    void printPersonalGoal(int personalGoalCard);
    void printCommonGoals(List<Integer> commonGoalCards);
    void sendChat() throws RemoteException;
    boolean askDraw() throws RemoteException;
    boolean putDraw() throws RemoteException;
    void printActions() throws RemoteException;
    void updateBookshelf() throws RemoteException;
    boolean reply() throws RemoteException;
}
