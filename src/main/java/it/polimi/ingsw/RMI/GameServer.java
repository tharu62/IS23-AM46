package it.polimi.ingsw.RMI;

import it.polimi.ingsw.MODEL.MESSAGE;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServer extends Remote {
    void connect(GameClient gc) throws RemoteException, InterruptedException;

    boolean login(String username, GameClient client) throws RemoteException;

    boolean loginFirst(String username, int LobbySize, GameClient client) throws RemoteException;

    boolean loginReconnect(String username, GameClient gc) throws RemoteException;

    boolean askDraw( String username, int a, int b) throws RemoteException;

    boolean askPut(String username, int a, int b, int c, int col) throws RemoteException;

    void endTurn(String username ) throws RemoteException;

    void sendMessage(MESSAGE message) throws RemoteException;

}
