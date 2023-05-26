package it.polimi.ingsw.VIEW.CLI;

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
    void printActions();
    void updateBookshelf() throws RemoteException;
    boolean reply() throws RemoteException;
    void printBookshelf(item[][] table);
    boolean askDraw() throws RemoteException;
    boolean putDraw() throws RemoteException;
    void endTurn() throws RemoteException;
}
