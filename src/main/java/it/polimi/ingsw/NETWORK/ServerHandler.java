package it.polimi.ingsw.NETWORK;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.RMI.ServerRMI;
import it.polimi.ingsw.TCP.SocketAccepter;
import java.rmi.RemoteException;


public class ServerHandler {

    public void run() throws RemoteException{

        GAME game = new GAME();
        CONTROLLER controller = new CONTROLLER();
        controller.setGame(game);
        ServerRMI serverRMI = new ServerRMI(controller, Settings.PORT_RMI);

        serverRMI.start();                                                                  // RMI CONNECTION //
        new SocketAccepter(controller, Settings.PORT_TCP).start();                          // TCP CONNECTION //

    }

}
