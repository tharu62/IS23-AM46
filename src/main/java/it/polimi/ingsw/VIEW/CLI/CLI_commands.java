package it.polimi.ingsw.VIEW.CLI;

import it.polimi.ingsw.MODEL.item;

import java.rmi.RemoteException;
import java.util.List;

public interface CLI_commands {

    void notifyCLI();
    String getUsername();
    int getLobbySize();
    boolean sendChat( ) throws RemoteException;
    void printBoard(item[][] grid);
    void printBookshelf(item[][] table);
    void printPersonalGoal() throws RemoteException;
    void printCommonGoals(List<Integer> commonGoalCards, List<Integer> commonGoalToken);
    void printActions();
    void printActionsChat();
    void printChatBuffer();
    void printScore();
    void colorTile(item[][] table, int i, int j);
    void askDraw() throws RemoteException, InterruptedException;
    void putDraw() throws RemoteException, InterruptedException;
    void endTurn() throws RemoteException;
    boolean replyDraw () throws InterruptedException;
    boolean replyPut () throws InterruptedException;
    void replyEndTurn();

}
