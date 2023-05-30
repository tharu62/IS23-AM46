package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.NETWORK.Client;
import it.polimi.ingsw.VIEW.CLI.CLI;

import java.rmi.RemoteException;

public interface COM {
    Client client = null;
    int getPersonalGoal( int PersonalGoalCardID , String username, CLI cli) throws RemoteException;
    boolean draw(String username, int row, int col) throws RemoteException;
    boolean put(String username, int col, int a , int b, int c , boolean put_valid) throws RemoteException;
    void bookshelf(CLI cli , String username) throws RemoteException;
    void sendChat(String text, String receiver, String username) throws RemoteException;
    void setPlayerToPlay( String username , boolean myTurn) throws RemoteException;
    void endTurn( boolean myTurn , String username) throws RemoteException;

}
