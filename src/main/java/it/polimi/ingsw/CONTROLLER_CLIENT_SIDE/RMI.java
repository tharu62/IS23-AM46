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
    public boolean draw(String username, int row, int col, boolean draw_valid, boolean reply_draw) throws RemoteException {
        draw_valid = ClientRMI.gs.askDraw(username, row, col);
        reply_draw = true;
        return draw_valid;
    }

    @Override
    public boolean put(String username, int col, int a , int b, int c, boolean put_valid, boolean reply_put) throws RemoteException {
        put_valid = ClientRMI.gs.askPutItem(username,col,a,b,c);
        reply_put = true;
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
    public void endTurn(boolean myTurn, String username) throws RemoteException {
        ClientRMI.gs.endTurn(username);
    }


    //TODO passare come argomento oggetto controller e non gli attributi, altrimenti non c'è passaggio di reference ma solo di value.
}
