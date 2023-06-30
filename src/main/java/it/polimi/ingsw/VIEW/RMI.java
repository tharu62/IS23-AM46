package it.polimi.ingsw.VIEW;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CommunicationProtocol;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMI implements CommunicationProtocol {
    public ClientRMI clientRMI;
    public RMI(ClientRMI client){
        this.clientRMI = client;
    }

    /**
     * This method sends a draw request to the Server and saves the reply value in the draw_valid attribute in controller.
     * Then reply_draw is set to true;
     *
     * @throws RemoteException
     */
    @Override
    public void draw(String username, int row, int col, CONTROLLER controller) throws RemoteException {
        controller.draw_valid = ClientRMI.gs.askDraw(username, row, col);
        controller.reply_draw = true;
    }

    /**
     * This method sends a put request to the Server and saves the reply value in the put_valid attribute in controller.
     * Then reply_put is set to true;
     *
     * @throws RemoteException
     */
    @Override
    public void put(String username, int col, int a , int b, int c, CONTROLLER controller) throws RemoteException {
        controller.put_valid = ClientRMI.gs.askPut(username,col,a,b,c);
        controller.reply_put = true;
    }

    /**
     * This method initialize a message with the given parameters and sends it to the Server.
     *
     * @throws RemoteException
     */
    @Override
    public void sendChat( String username, String text, String receiver) throws RemoteException {
        MESSAGE m = new MESSAGE();
        m.header[0] = username;
        m.header[1] = receiver;
        m.text = text;
        ClientRMI.sendMessage(m);
    }

    /**
     * This method sends an end turn request to the Server.
     *
     * @throws RemoteException
     */
    @Override
    public void endTurn( String username) throws RemoteException {
        ClientRMI.gs.endTurn(username);
    }


    @Override
    public void replaceClient(ClientTCP clientTCP) {
        //
    }

    /**
     * this method change the reference to the client RMI with the given client RMI.
     *
     * @param clientRMI is the new reference to use.
     */
    @Override
    public void replaceClient(ClientRMI clientRMI) {
        this.clientRMI = clientRMI;
    }

    /**
     * This method starts the client RMI communication with the Server.
     * @throws Exception
     */
    @Override
    public void startClientRMI() throws Exception {
        clientRMI.start();
    }

}
