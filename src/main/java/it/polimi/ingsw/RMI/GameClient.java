package it.polimi.ingsw.RMI;

import it.polimi.ingsw.MODEL.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GameClient extends Remote {
    void receive(String message) throws RemoteException;
    void receivePlayers( List< String > players ) throws RemoteException;
    void receiveBoard(item[][] grid) throws RemoteException;
    void receiveCommonGoals(List<COMMON_GOAL_CARD> list) throws RemoteException;
    void receivePlayerToPlay(String username) throws RemoteException;


}
