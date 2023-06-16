package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.VIEW.CLI.CLI;
import java.rmi.RemoteException;

public interface CommunicationProtocol {
    int getPersonalGoal( int PersonalGoalCardID , String username) throws RemoteException;
    void draw(String username, int row, int col, CONTROLLER controller) throws RemoteException;
    void put(String username, int col, int a , int b, int c , CONTROLLER controller) throws RemoteException;
    void bookshelf(CLI cli , String username) throws RemoteException;
    void sendChat(String text, String receiver, String username) throws RemoteException;
    void endTurn( String username ) throws RemoteException;

}
