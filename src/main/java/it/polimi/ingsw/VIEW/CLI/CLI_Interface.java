package it.polimi.ingsw.VIEW.CLI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
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
    void sendChat( CONTROLLER controller ) throws RemoteException;
    void printActions(CONTROLLER controller);
    void updateBookshelf(CONTROLLER controller) throws RemoteException;
    boolean reply(CONTROLLER controller) throws RemoteException;
    void printBookshelf(item[][] table);
    boolean askDraw(CONTROLLER controller) throws RemoteException, InterruptedException;
    boolean putDraw(CONTROLLER controller) throws RemoteException, InterruptedException;
    void endTurn(CONTROLLER controller) throws RemoteException;
    void colorTile(item[][] table, int i, int j);
    int replyPersonal(CONTROLLER controller);
    boolean replyDraw (CONTROLLER controller) throws InterruptedException;
    boolean replyPut (CONTROLLER controller) throws InterruptedException;
    void notifyThread();
}
