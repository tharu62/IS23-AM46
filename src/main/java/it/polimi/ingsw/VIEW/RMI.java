package it.polimi.ingsw.VIEW;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CommunicationProtocol;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;

import java.rmi.RemoteException;

public class RMI implements CommunicationProtocol {
    public ClientRMI clientRMI;
    public RMI(ClientRMI client){
        this.clientRMI = client;
    }

    @Override
    public void draw(String username, int row, int col, CONTROLLER controller) throws RemoteException {
        controller.draw_valid = ClientRMI.gs.askDraw(username, row, col);
        controller.reply_draw = true;
    }

    @Override
    public void put(String username, int col, int a , int b, int c, CONTROLLER controller) throws RemoteException {
        controller.put_valid = ClientRMI.gs.askPut(username,col,a,b,c);
        controller.reply_put = true;
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
    public void endTurn( String username) throws RemoteException {
        ClientRMI.gs.endTurn(username);
    }

    @Override
    public void replaceClient(ClientTCP clientTCP) {
        //
    }

}
