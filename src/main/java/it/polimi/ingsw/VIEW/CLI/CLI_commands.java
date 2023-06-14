package it.polimi.ingsw.VIEW.CLI;

import it.polimi.ingsw.MODEL.item;

import java.rmi.RemoteException;
import java.util.List;

public interface CLI_commands {

    void notify(String message);
    String getUsername();
    int getLobbySize();
    void printBoard(item[][] grid);
    void printPersonalGoal() throws RemoteException;
    void printCommonGoals(List<Integer> commonGoalCards, List<Integer> commonGoalToken);
    boolean sendChat( ) throws RemoteException;
    void printActions();
    void printActionsChat();
    void printChatBuffer();
    void updateBookshelf() throws RemoteException;
    void printBookshelf(item[][] table);
    void askDraw() throws RemoteException, InterruptedException;
    void putDraw() throws RemoteException, InterruptedException;
    void endTurn() throws RemoteException;
    void colorTile(item[][] table, int i, int j);
    int replyPersonal();
    boolean replyDraw () throws InterruptedException;
    boolean replyPut () throws InterruptedException;
    boolean replyBookshelf ();
    void replyEndTurn();

}
