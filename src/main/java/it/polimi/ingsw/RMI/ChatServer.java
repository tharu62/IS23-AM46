package it.polimi.ingsw.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
    void login(ChatClient cc) throws RemoteException;
    void send (String message) throws RemoteException;
}
