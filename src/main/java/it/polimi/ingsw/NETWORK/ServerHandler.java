package it.polimi.ingsw.NETWORK;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.RMI.ClientHandlerRMI;
import it.polimi.ingsw.RMI.ping;
import it.polimi.ingsw.TCP.SocketAccepter;
import java.rmi.RemoteException;

/**
 * This class generates a new MODEL ( game ) and a new CONTROLLER ( controller ) for the server,
 * then it starts a new ClientHandlerRMI ( for the RMI communication protocol ) AND
 * a new SocketAccepter ( for the TCP communication protocol).
 * The SocketAcceptor is started in a separate Thread.
 */
public class ServerHandler{

    public void run() throws RemoteException {

        GAME game = new GAME();
        CONTROLLER controller = new CONTROLLER();
        controller.setGame(game);
        ClientHandlerRMI clientHandlerRMI = null;
        clientHandlerRMI = new ClientHandlerRMI(controller, Settings.PORT_RMI);

        SocketAccepter socketAccepter = new SocketAccepter(controller, Settings.PORT_TCP);
        ping ping = new ping(controller, clientHandlerRMI.clientsRMI);


        clientHandlerRMI.start();                       // RMI CONNECTION //
        socketAccepter.start();                         // TCP CONNECTION //
        ping.start();                                   // RMI PING //

    }

}
