package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.NETWORK.Client;
import it.polimi.ingsw.VIEW.CLI.CLI;

import java.rmi.RemoteException;

public interface COM {
    Client client = null;
    int getPersonalGoal( int PersonalGoalCardID , String username, CLI cli) throws RemoteException;
    boolean draw(String username, int row, int col, boolean draw_valid, boolean reply_draw) throws RemoteException;
    boolean put(String username, int col, int a , int b, int c , boolean put_valid, boolean reply_put) throws RemoteException;
    void bookshelf(CLI cli , String username) throws RemoteException;
    void sendChat(String text, String receiver, String username) throws RemoteException;
    void endTurn( boolean myTurn , String username) throws RemoteException;

}
