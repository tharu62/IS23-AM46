package it.polimi.ingsw.RMI;

import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.MODEL.COMMON_GOAL_CARD;
import it.polimi.ingsw.MODEL.PERSONAL_GOAL_CARD;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GameClient extends Remote {
    void ping() throws RemoteException;
    void receiveMessage( MESSAGE message) throws RemoteException;
    void receiveLOG(String message) throws RemoteException;
    void receiveDisconnectedPlayer(String username) throws RemoteException;
    void receivePlayers( List< String > players ) throws RemoteException;
    void receiveBoard( item[][] grid ) throws RemoteException;
    void receiveCommonGoals( List<Integer> cardID, List<Integer> token ) throws RemoteException;
    void receiveScore(int score) throws RemoteException;
    void receivePlayerToPlay( String username ) throws RemoteException;
    void receiveLastRound() throws RemoteException;
    void receiveWinner( String winner ) throws RemoteException;
    void receivePersonalGoal( int p ) throws RemoteException;
    void receiveBookshelf(item[][] bookshelf) throws RemoteException;
}
