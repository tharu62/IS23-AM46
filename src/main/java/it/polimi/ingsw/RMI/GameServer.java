package it.polimi.ingsw.RMI;

import it.polimi.ingsw.MODEL.*;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServer extends Remote {
    void login(GameClient cc) throws RemoteException;
    void sendMessage(String message) throws RemoteException;
    item[][] sendBoard() throws RemoteException;

}
