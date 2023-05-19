package it.polimi.ingsw.NETWORK;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.RMI.GameClient;
import it.polimi.ingsw.RMI.ServerRMI;
import it.polimi.ingsw.TCP.ClientHandler;
import it.polimi.ingsw.TCP.SocketAccepter;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler {

    public void run() throws RemoteException{

        List<GameClient> clientsRMI = new ArrayList<>();
        List<ClientHandler> clientsTCP = new ArrayList<>();
        GAME game = new GAME();
        CONTROLLER controller = new CONTROLLER();
        controller.setGame(game);
        ServerRMI serverRMI = new ServerRMI(controller, Settings.PORT_RMI, clientsTCP);

        controller.start();                                                             // CONTROLLER LOOP STARTED //
        new Thread( serverRMI.start() ).start();                                            // RMI CONNECTION //
        new SocketAccepter(controller, Settings.PORT_TCP, clientsRMI).start();              // TCP CONNECTION //

    }

}
