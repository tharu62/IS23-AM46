package it.polimi.ingsw.RMI;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServer extends Remote {
    void connect(GameClient cc) throws RemoteException, InterruptedException;

    boolean login(String username, GameClient client) throws RemoteException;

    boolean loginFirst(String username, int LobbySize, GameClient client) throws RemoteException;

    boolean loginReconnect(String username) throws RemoteException;
    int sendPersonalGoal(String username) throws RemoteException;

    boolean askDraw( String username, int a, int b) throws RemoteException;

    boolean askPutItem( String username, int a, int b, int c, int col) throws RemoteException;

    int askCheckScore( String username ) throws RemoteException;

    boolean endTurn( String username ) throws RemoteException;

    void sendMessage(MESSAGE message) throws RemoteException;

    void sendBookshelf(String username, GameClient gc) throws RemoteException;

    void askLobbyReady(GameClient gc) throws RemoteException;
}
