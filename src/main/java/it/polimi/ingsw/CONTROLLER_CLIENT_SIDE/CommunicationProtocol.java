package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface CommunicationProtocol {

    void draw(String username, int row, int col, CONTROLLER controller) throws RemoteException;
    void put(String username, int col, int a , int b, int c , CONTROLLER controller) throws RemoteException;
    void sendChat(String text, String receiver, String username) throws RemoteException;
    void endTurn( String username ) throws RemoteException;
    void replaceClient(ClientTCP clientTCP);
    void replaceClient(ClientRMI clientRMI);
    void startClientRMI() throws Exception;

}
