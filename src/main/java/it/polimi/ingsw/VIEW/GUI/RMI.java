package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CommunicationProtocol;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.VIEW.CLI.CLI;

import java.rmi.RemoteException;

public class RMI implements CommunicationProtocol {
    ClientRMI client;

    public RMI(ClientRMI client) {
        this.client = client;
    }

    @Override
    public int getPersonalGoal(int PersonalGoalCardID, String username) throws RemoteException {
        return 0;
    }

    @Override
    public void draw(String username, int row, int col, CONTROLLER controller) throws RemoteException {

    }

    @Override
    public void put(String username, int col, int a, int b, int c, CONTROLLER controller) throws RemoteException {

    }

    @Override
    public void bookshelf(CLI cli, String username) throws RemoteException {

    }

    @Override
    public void sendChat(String text, String receiver, String username) throws RemoteException {

    }

    @Override
    public void endTurn(String username) throws RemoteException {

    }
}
