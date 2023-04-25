package it.polimi.ingsw.RMI;

import it.polimi.ingsw.MODEL.PERSONAL_GOAL_CARD;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServer extends Remote {
    void connect(GameClient cc) throws RemoteException;

    boolean login(String username) throws RemoteException;
    boolean loginFirst(String username, int LobbySize) throws RemoteException;
    PERSONAL_GOAL_CARD sendPersonalGoal(String username) throws RemoteException;
    void startTurn(String username) throws RemoteException;
    void sendMessage(String message) throws RemoteException;

}
