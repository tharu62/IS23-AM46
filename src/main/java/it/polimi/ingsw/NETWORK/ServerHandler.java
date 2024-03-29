package it.polimi.ingsw.NETWORK;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.RMI.ClientHandlerRMI;
import it.polimi.ingsw.RMI.pingRMI;
import it.polimi.ingsw.TCP.SocketAccepter;
import it.polimi.ingsw.TCP.pingTCP;

import java.rmi.RemoteException;

/**
 * This class generates a new MODEL ( game ) and a new CONTROLLER ( controller ) for the server,
 * then it starts a new ClientHandlerRMI ( for the RMI communication protocol ) AND
 * a new SocketAccepter ( for the TCP communication protocol).
 * The SocketAcceptor is started in a separate Thread.
 */
public class ServerHandler {

    public void run() throws RemoteException {

        GAME game = new GAME();
        CONTROLLER controller = new CONTROLLER();
        controller.setGame(game);
        ClientHandlerRMI clientHandlerRMI;
        clientHandlerRMI = new ClientHandlerRMI(controller, Settings.PORT_RMI);

        SocketAccepter socketAccepter = new SocketAccepter(controller, Settings.PORT_TCP);
        pingRMI pingRMI = new pingRMI(clientHandlerRMI, controller, clientHandlerRMI.clientsRMI);
        pingTCP pingTCP = new pingTCP(controller, controller.clientsTCP);

        clientHandlerRMI.start();                       // RMI CONNECTION //
        socketAccepter.start();                         // TCP CONNECTION //
        pingRMI.start();                                   // RMI PING //
        pingTCP.start();                                   // TCP PING //
    }

}
