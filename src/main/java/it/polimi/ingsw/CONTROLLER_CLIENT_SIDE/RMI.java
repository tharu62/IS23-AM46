package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.VIEW.CLI.CLI;

import java.rmi.RemoteException;

public class RMI implements COM{
    public ClientRMI clientRMI;
    public RMI(ClientRMI client){
        this.clientRMI = client;
    }

    @Override
    public int getPersonalGoal(int PersonalGoalCardID , String username, CLI cli) throws RemoteException {
        PersonalGoalCardID = ClientRMI.gs.sendPersonalGoal(username);
        return PersonalGoalCardID;
    }

    @Override
    public boolean draw(String username, int row, int col) throws RemoteException {
        return ClientRMI.gs.askDraw(username, row, col);
    }

    @Override
    public boolean put(String username, int col, int a , int b, int c, boolean put_valid) throws RemoteException {
        put_valid = ClientRMI.gs.askPutItem(username,col,a,b,c);
        return put_valid;
    }

    @Override
    public void bookshelf(CLI cli , String username) throws RemoteException {
        cli.cmd.printBookshelf(ClientRMI.gs.sendBookshelf(username));
    }

    @Override
    public void sendChat( String username, String text, String receiver) throws RemoteException {
        MESSAGE m = new MESSAGE();
        m.header[0] = username;
        m.header[1] = receiver;
        m.text = text;
        ClientRMI.sendMessage(m);
    }

    @Override
    public void setPlayerToPlay(String username , boolean myTurn) throws RemoteException {
        myTurn = ClientRMI.gs.askMyTurn( username );
    }

    @Override
    public void endTurn(boolean myTurn, String username) throws RemoteException {
        myTurn = false;
        ClientRMI.gs.endTurn(username);
    }


}
