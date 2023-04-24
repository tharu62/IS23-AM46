package it.polimi.ingsw.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameClient extends Remote {
    void receive(String message) throws RemoteException;

}
